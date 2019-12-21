<?php
/**
 * Created by PhpStorm.
 * User: yinaoxiong
 * Date: 2017/9/16
 * Time: 2:42
 */

class Caculate_model extends CI_Model
{
    public function __construct()
    {
        parent::__construct();
        $this->load->database();
    }
    public function get_t()
    {
        //$sql="SELECT * from task";
        $query=$this->db->get('task');
        return $query->result_array();
    }

    public function get_w()
    {
        //$sql="SELECT * from worker";
        $query=$this->db->get('worker');
        return $query->result_array();
    }

    public function get_result()
    {
        $query=$this->db->get('result');
        return $query->result_array();
    }

    public function get_need_result()
    {
        $query=$this->db->get('need_result');
        return $query->result_array();
    }
    public function insert($data)
    {
        return $this->db->insert('result',$data);
    }

    public function last_insert($data)
    {
        return $this->db->insert('last_result',$data);
    }

    public function need_insert($data)
    {
        return $this->db->insert('need_result',$data);
    }

}