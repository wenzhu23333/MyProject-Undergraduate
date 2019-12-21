<?php
/**
 * Created by PhpStorm.
 * User: yinaoxiong
 * Date: 2017/9/16
 * Time: 2:22
 */

set_time_limit(36000); //时间限制

class Caculate extends CI_Controller
{
    private $data = "http://restapi.amap.com/v3/direction/";
    private $bk_data = 'http://restapi.amap.com/v4/direction/';

    public function __construct()
    {
        parent::__construct();
        $this->load->model('Caculate_model');
    }

    private function httpget($url)
    {
        $ch = curl_init();

        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        $output = json_decode(curl_exec($ch), true);
        //var_dump($output);
        curl_close($ch);
        return $output;
    }

    private function walking($key, $o_lon, $o_lat, $d_lon, $d_lat)
    {
        $walking_url = $this->data . 'walking?origin=';
        $walking_url = $walking_url . $o_lon . ',' . $o_lat . '&' . 'destination=' . $d_lon . ',' . $d_lat . '&' . 'key=' . $key;
        $result = $this->httpget($walking_url);
        return $result;
    }

    private function bicycling($key, $o_lon, $o_lat, $d_lon, $d_lat)
    {
        $bicycling_url = $this->bk_data . 'bicycling?origin=';
        $bicycling_url = $bicycling_url . $o_lon . ',' . $o_lat . '&' . 'destination=' . $d_lon . ',' . $d_lat . '&' . 'key=' . $key;
        $result = $this->httpget($bicycling_url);
        return $result;
    }

    private function driving($key, $o_lon, $o_lat, $d_lon, $d_lat)
    {
        $driving_url = $this->data . 'driving?origin=';
        $driving_url = $driving_url . $o_lon . ',' . $o_lat . '&' . 'destination=' . $d_lon . ',' . $d_lat . '&' . 'key=' . $key;
        $result = $this->httpget($driving_url);
        return $result;
    }

    private function bus($key, $o_lon, $o_lat, $d_lon, $d_lat)
    {
        $bus_url = $this->data . 'transit/integrated?origin=';
        $bus_url = $bus_url . $o_lon . ',' . $o_lat . '&' . 'destination=' . $d_lon . ',' . $d_lat . '&' . 'key=' . $key.'&'.'city=广州';
        $result = $this->httpget($bus_url);
        //var_dump($result);
        return $result;

    }

    public function index()
    {
        $key = 'b603c040338834839b1878e44a78b531';
        $o_data = $this->Caculate_model->get_w();
        $d_data = $this->Caculate_model->get_t();
        $n = 0;
        foreach ($d_data as $i) {
            foreach ($o_data as $j) {
                $o_lon = $j['worker_ln'];
                $o_lat = $j['worker_la'];
                $d_lon = $i['task_ln'];
                $d_lat = $i['task_la'];
                $walking_result = $this->walking($key, $o_lon, $o_lat, $d_lon, $d_lat);
                $bicycling_result = $this->bicycling($key, $o_lon, $o_lat, $d_lon, $d_lat);
                $driving_result = $this->driving($key, $o_lon, $o_lat, $d_lon, $d_lat);
                $bus_result = $this->bus($key, $o_lon, $o_lat, $d_lon, $d_lat);
                $need_walking_result = array();
                $need_bus_result = array();
                $need_bicycling_result = array();
                $need_driving_result = array();

                if ($walking_result['status'] == '0') {
                    $need_walking_result['status'] = '0';
                } else {
                    $need_walking_result = array(
                        'status' => '1',
                        'duration' => $walking_result['route']['paths'][0]['duration']
                    );
                }
                if ($bus_result['status'] == '0') {
                    $need_bus_result['status'] = '0';
                } else {
                    $need_bus_result = array(
                        'status' => '1',
                        'cost' => $bus_result['route']['transits'][0]['cost'],
                        'duration' => $bus_result['route']['transits'][0]['duration']
                    );
                }
                if ($driving_result['status'] == '0') {
                    $need_driving_result['status'] = '0';
                } else {
                    $need_driving_result = array(
                        'status' => '1',
                        'cost' => $driving_result['route']['paths'][0]['distance'] * 0.5,
                        'duration' => $driving_result['route']['paths'][0]['duration']
                    );
                }
                if ($bicycling_result['errcode'] != 0) {
                    $need_bicycling_result['status'] = 0;
                } else {
                    $need_bicycling_result = array(
                        'status' => 1,
                        'cost' => $bicycling_result['data']['paths'][0]['duration'] / (18000),
                        'duration' => $bicycling_result['data']['paths'][0]['duration']
                    );
                }

                $insert_data = array(
                    'task_lon' => $d_lon,
                    'task_lat' => $d_lat,
                    'worker_lon' => $o_lon,
                    'worker_lat' => $o_lat,
                    'walking_result' => json_encode($need_walking_result),
                    'driving_result' => json_encode($need_driving_result),
                    'bicycling_result' => json_encode($need_bicycling_result),
                    'bus_result' => json_encode($need_bus_result)
                );
                $this->Caculate_model->need_insert($insert_data);



            }
        }


        echo 'end';

    }

    public function jisuan()
    {
        $t_data=$this->Caculate_model->get_need_result();
        foreach ($t_data as $i)
        {
            $insert_data=array();
            $walking_result=json_decode($i['walking_result'],true);
            $driving_result=json_decode($i['driving_result'],true);
            $bicycling_result=json_decode($i['bicycling_result'],true);
            $bus_result=json_decode($i['bus_result'],true);
            if($walking_result['status']=='1')
                $insert_data['walking_time']=$walking_result['duration'];
            else
                $insert_data['walking_time']='max';
            if($driving_result['status']=='0')
            {
                $insert_data['driving_cost']='max';
                $insert_data['driving_time']='max';
            }
            else
            {

                $insert_data['driving_cost']=$driving_result['cost'];
                $insert_data['driving_time']=$driving_result['duration'];
            }
            if($bicycling_result['status']=='0')
            {
                $insert_data['bicycling_cost']='max';
                $insert_data['bicycling_time']='max';
            }
            else
            {

                $insert_data['bicycling_cost']=$bicycling_result['cost'];
                $insert_data['bicycling_time']=$bicycling_result['duration'];
            }
            if($bus_result['status']=='0')
            {
                $insert_data['bus_cost']='max';
                $insert_data['bus_time']='max';
            }
            else
            {
                if(is_array($bus_result['cost']))
                    $insert_data['bus_cost']='unknow';
                else
                    $insert_data['bus_cost']=$bus_result['cost'];
                $insert_data['bus_time']=$bus_result['duration'];
            }
            $insert_data['task_lon']=$i['task_lon'];
            $insert_data['task_lat']=$i['task_lat'];
            $insert_data['worker_lon']=$i['worker_lon'];
            $insert_data['worker_lat']=$i['worker_lat'];

            $this->Caculate_model->last_insert($insert_data);
        }
        echo 'end';
    }

    public function test()
    {
        $key = 'b603c040338834839b1878e44a78b531';
        $o_data = $this->Caculate_model->get_w();
        $d_data = $this->Caculate_model->get_t();
        $n = 0;
        $o_lon = 113.070722;
        $o_lat = 23.04121;
        $d_lon = 113.124366;
        $d_lat = 23.005696;
        $walking_result = $this->walking($key, $o_lon, $o_lat, $d_lon, $d_lat);
        $bicycling_result = $this->bicycling($key, $o_lon, $o_lat, $d_lon, $d_lat);
        $driving_result = $this->driving($key, $o_lon, $o_lat, $d_lon, $d_lat);
        $bus_result = $this->bus($key, $o_lon, $o_lat, $d_lon, $d_lat);
        $need_walking_result = array();
        $need_bus_result = array();
        $need_bicycling_result = array();
        $need_driving_result = array();

        if ($walking_result['status'] == '0') {
            $need_walking_result['status'] = '0';
        } else {
            $need_walking_result = array(
                'status' => '1',
                'duration' => $walking_result['route']['paths'][0]['duration']
            );
        }
        if ($bus_result['status'] == '0') {
            $need_bus_result['status'] = '0';
        } else {
            $need_bus_result = array(
                'status' => '1',
                'cost' => $bus_result['route']['transits'][0]['cost'],
                'duration' => $bus_result['route']['transits'][0]['duration']
            );
        }
        if ($driving_result['status'] == '0') {
            $need_driving_result['status'] = '0';
        } else {
            $need_driving_result = array(
                'status' => '1',
                'cost' => $driving_result['route']['paths'][0]['distance'] * 0.5,
                'duration' => $driving_result['route']['paths'][0]['duration']
            );
        }
        if ($bicycling_result['errcode'] != 0) {
            $need_bicycling_result['status'] = 0;
        } else {
            $need_bicycling_result = array(
                'status' => 1,
                'cost' => $bicycling_result['data']['paths'][0]['duration'] / (18000),
                'duration' => $bicycling_result['data']['paths'][0]['duration']
            );
        }

        $insert_data = array(
            'task_lon' => $d_lon,
            'task_lat' => $d_lat,
            'worker_lon' => $o_lon,
            'worker_lat' => $o_lat,
            'walking_result' => json_encode($need_walking_result),
            'driving_result' => json_encode($need_driving_result),
            'bicycling_result' => json_encode($need_bicycling_result),
            'bus_result' => json_encode($need_bus_result)
        );
       // $this->Caculate_model->need_insert($insert_data);
    }

}