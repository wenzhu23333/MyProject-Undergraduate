function cost=c_cost(d)
if(d<2986.12)
    cost=65+9.78e-5*d;
elseif(d<16237.59)
    cost=64.25+3.51e-4*d;
elseif(d<23028.40)
    cost=59.69+6.31e-4*d;
else
    cost=57.83+7.12e-4*d;
end
