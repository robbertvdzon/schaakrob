
difference(){
	union(){
        // omrand voor bout midden
        translate([0,-19,-0.5]){
            rotate([90,0,0]){
                cylinder(h=5, r=6, $fn=100, center=false);
            }
        }         

        translate([-10.5,-13,11]){
           // cube([21,20.5,3], center=false);
        }         
        translate([-10.5,-13,-15]){
          //  cube([21,20.5,3], center=false);
        }         
        
        // 4 assen
        translate([-7.5,7.5,-13.5]){
            rotate([90,0,0]){
                cylinder(h=20.5, r=4, $fn=100, center=false);
            }
        }         
        translate([7.5,7.5,-13.5]){
            rotate([90,0,0]){
                cylinder(h=20.5, r=4, $fn=100, center=false);
            }
        }         
        translate([-7.5,7.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=20.5, r=4, $fn=100, center=false);
            }
        }         
        translate([7.5,7.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=20.5, r=4, $fn=100, center=false);
            }
        }         

        // 4 omranden voor bout
        translate([-7.5,-7,-13.5]){
            rotate([90,0,0]){
                cylinder(h=7, r1=4,  r2=6,$fn=100, center=false);
            }
        }         
        translate([7.5,-7,-13.5]){
            rotate([90,0,0]){
                cylinder(h=7, r1=4,  r2=6,$fn=100, center=false);
            }
        }         
        translate([-7.5,-7,12.5]){
            rotate([90,0,0]){
                cylinder(h=7, r1=4,  r2=6,$fn=100, center=false);
            }
        }         
        translate([7.5,-7,12.5]){
            rotate([90,0,0]){
                cylinder(h=7, r1=4,  r2=6,$fn=100, center=false);
            }
        }         



        translate([-7.5,-10-3,-0.5]){
            rotate([0,90,0]){
                cylinder(h=15, r=7.5, $fn=100, center=false);
            }
        }

        translate([0,-10,-0.5]){
            rotate([90,0,0]){
                cylinder(h=10, r=3, $fn=100, center=false);
            }
        }
      
        translate([0,0,-33/2-2]){
            rotate([0,0,90]){
                cylinder(h=53-17, r=7.5, $fn=100, center=false);
            }
        }
        

        


	}
	union() {
        // plek voor bout midden
        translate([0,-20,-0.5]){
            rotate([90,0,0]){
                cylinder(h=4.1, r=5.7/2, $fn=100, center=false);
            }
        }         
        
        // 4 omranden voor bout
        translate([-7.5,-10,-13.5]){
            rotate([90,0,0]){
                cylinder(h=4.1, r=5.7/2, $fn=100, center=false);
            }
        }      
        translate([7.5,-10,-13.5]){
            rotate([90,0,0]){
                cylinder(h=4.1, r=5.7/2, $fn=100, center=false);
            }
        }         
        translate([-7.5,-10,12.5]){
            rotate([90,0,0]){
                cylinder(h=4.1, r=5.7/2, $fn=100, center=false);
            }
        }         
        translate([7.5,-10,12.5]){
            rotate([90,0,0]){
                cylinder(h=4.1, r=5.7/2, $fn=100, center=false);
            }
        }    
        



        for (hoek =[0:45:360])
        rotate([0,0,hoek]){

            translate([-1,-5,-25]){
                rotate([0,0,0]){
                    cube([2,10,65], center=false);
                }
            }
        }
        translate([0,0,-33/2+2]){
            rotate([0,0,90]){
                cylinder(h=28, r=5, $fn=100, center=false);
            }
        }
        translate([0,0,-35]){
            rotate([0,0,90]){
                cylinder(h=100, r=4, $fn=100, center=false);
            }
        }

        // bovenste as
        translate([0,-15-4.8,-30]){
            rotate([0,0,90]){
                cylinder(h=50, r=1.5, $fn=100, center=false);
            }
        }
        translate([-60,-10-3,-0.5]){
            rotate([0,90,0]){
                cylinder(h=200, r=4, $fn=100, center=false);
            }
        }
        translate([0,-10-3,-0.5]){
            rotate([90,0,0]){
                cylinder(h=100, r=2, $fn=100, center=false);
            }
        }
        
        // gaten voor riemhouders
        translate([-7.5,8.5,-13.5]){
            rotate([90,0,0]){
                cylinder(h=25, r=2, $fn=100, center=false);
            }
        }         
        translate([7.5,8.5,-13.5]){
            rotate([90,0,0]){
                cylinder(h=25, r=2, $fn=100, center=false);
            }
        }         

        translate([-7.5,8.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=25, r=2, $fn=100, center=false);
            }
        }         
        translate([7.5,8.5,12.5]){
            rotate([90,0,0]){
                cylinder(h=25, r=2, $fn=100, center=false);
            }
        }             


	}
}
