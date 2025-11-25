breedte = 22.7;
hele_breedte = 32.5;
diepte = 27;
dikte_rand = 2.5;
marge = 0.2;


motor_breedte = hele_breedte+marge*2;
motor_lengte = diepte+marge*2;

breedte_ruimte_tussen_2_motoren = 2;
lengte_ruimte_tussen_2_motoren = 2;


difference(){
	union(){
         
        
        // gaten voor bout
        translate([hele_breedte+hele_breedte/2+breedte_ruimte_tussen_2_motoren-2,diepte/2-3-10,0]){
            rotate([0,0,0]){
                cylinder(h=8, r=5, $fn=100, center=false);
            }

        }           
        translate([hele_breedte+hele_breedte/2+breedte_ruimte_tussen_2_motoren-lengte_ruimte_tussen_2_motoren,-diepte-2-diepte/2+3+10,0]){
            rotate([0,0,0]){
                cylinder(h=8, r=5, $fn=100, center=false);
            }
        }           

        translate([-hele_breedte/2+2,diepte/2-3-10,0]){
            rotate([0,0,0]){
                cylinder(h=8, r=5, $fn=100, center=false);
            }
        }           
        translate([-hele_breedte/2+2,-diepte-2-diepte/2+3+10,0]){
            rotate([0,0,0]){
                cylinder(h=8, r=5, $fn=100, center=false);
            }
        }              


        translate([-motor_breedte/2-4,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+6,-2]){
            cube([
            motor_breedte*2+breedte_ruimte_tussen_2_motoren*2+5.5
            ,motor_lengte*2+lengte_ruimte_tussen_2_motoren*2-13
            ,4], center=false);
        }       


        translate([-motor_breedte/2+9.5+2,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+6,0]){
            cube([
            motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-19-4
            ,3
            ,21], center=false);
        }       

        translate([-motor_breedte/2+9.5+2,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+motor_lengte*2+2+lengte_ruimte_tussen_2_motoren*2-4-8,0]){
            cube([
            motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-19-4
            ,3
            ,21], center=false);
        }       


        translate([-motor_breedte/2+9.5+2,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+6,0]){
            cube([
            2
            ,motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-24
            ,32], center=false);
        }       

        translate([-motor_breedte/2+30+motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-30-4-9.5,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+6,0]){
            cube([
            2
            ,motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-24
            ,32], center=false);
        }       

  



	}
	union() {
 
        // gat voor veer
        translate([1,15,10.5]){
            rotate([90,0,0]){
                cylinder(h=1000, r=1.5, $fn=100, center=false);
            }
        }   
        translate([17.5+16.5,15.5,10.5]){
            rotate([90,0,0]){
                cylinder(h=1000, r=1.5, $fn=100, center=false);
            }
        }   
        translate([-25,-30,21.5]){
            rotate([0,90,0]){
                cylinder(h=400, r=1.5, $fn=100, center=false);
            }
        }   
        translate([-25,2,21.5]){
            rotate([0,90,0]){
                cylinder(h=400, r=1.5, $fn=100, center=false);
            }
        }   


        // schroefgaten voor geleider
        translate([-20,5-1.2-8,27]){
            rotate([0,90,0]){
                cylinder(h=100, r=1.2, $fn=100, center=false);
            }
        }           
        translate([-20,-31-1.2+8,27]){
            rotate([0,90,0]){
                cylinder(h=100, r=1.2, $fn=100, center=false);
            }
        }       



        // schroefgaten
        translate([hele_breedte+hele_breedte/2+breedte_ruimte_tussen_2_motoren-2,diepte/2-3-10,-10]){
            rotate([0,0,0]){
                cylinder(h=20, r=4.8/2, $fn=100, center=false);
            }

        }           
        translate([hele_breedte+hele_breedte/2+breedte_ruimte_tussen_2_motoren-lengte_ruimte_tussen_2_motoren,-diepte-2-diepte/2+3+10,-10]){
            rotate([0,0,0]){
                cylinder(h=20, r=4.8/2, $fn=100, center=false);
            }
        }           

        translate([-hele_breedte/2+2,diepte/2-3-10,-10]){
            rotate([0,0,0]){
                cylinder(h=20, r=4.8/2, $fn=100, center=false);
            }
        }           
        translate([-hele_breedte/2+2,-diepte-2-diepte/2+3+10,-10]){
            rotate([0,0,0]){
                cylinder(h=20, r=4.8/2, $fn=100, center=false);
            }
        }       
    
        
        translate([-20.25,-19.5,28]){
            rotate([180,0,0]){      

                // as gaten voor schuif assen        
                translate([75/2,50,17.5]){
                    rotate([90,0,0]){
                        cylinder(h=200, r=4.1, $fn=100, center=false);
                    }
                }
                translate([-10,-10+5,6.5]){
                    rotate([0,90,0]){
                       cylinder(h=200, r=4.1, $fn=100, center=false);
                    }
                }        
               
    
                
                //lager kruizen
                translate([75/2,20,17.5]){
                    rotate([90,0,0]){
                        for (hoek =[0:45:360])
                        rotate([0,0,hoek]){
                            translate([-1,-6,-30]){
                                rotate([0,0,0]){
                                    cube([2,6,90], center=false);
                                }
                            }
                        }
                    }
                }
                
                translate([10,-10+5,6.5]){
                    rotate([0,90,0]){
                        for (hoek =[0:45:360])
                        rotate([0,0,hoek]){
                            translate([-1,-6,-30]){
                                rotate([0,0,0]){
                                    cube([2,6,150], center=false);
                                }
                            }
                        }
                    }
                }
            }
       }        
    
        

	}
}
