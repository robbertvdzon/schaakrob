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
        
        translate([-motor_breedte/2,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren,0]){
            cube([
            motor_breedte*2+breedte_ruimte_tussen_2_motoren*2
            ,motor_lengte*2+lengte_ruimte_tussen_2_motoren*2
            ,4], center=false);
        }       


        translate([-motor_breedte/2+15,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+6,0]){
            cube([
            motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-30
            ,4
            ,18], center=false);
        }       

        translate([-motor_breedte/2+15,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+motor_lengte*2+lengte_ruimte_tussen_2_motoren*2-4-6,0]){
            cube([
            motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-30
            ,4
            ,18], center=false);
        }       


        translate([-motor_breedte/2+15,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+6,0]){
            cube([
            4
            ,motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-24
            ,30], center=false);
        }       

        translate([-motor_breedte/2+15+motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-30-4,-motor_lengte-motor_lengte/2-lengte_ruimte_tussen_2_motoren+6,0]){
            cube([
            4
            ,motor_breedte*2+breedte_ruimte_tussen_2_motoren*2-24
            ,30], center=false);
        }       





        

	}
	union() {

        // ruimte voor assen
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2,diepte/2+4,-1]){
            rotate([0,0,0]){
                cylinder(h=20, r=6.5, $fn=100, center=false);
            }
        }        
        translate([(hele_breedte+breedte_ruimte_tussen_2_motoren)/2,-diepte-lengte_ruimte_tussen_2_motoren-diepte/2-4,-1]){
            rotate([0,0,0]){
                cylinder(h=20, r=6.5, $fn=100, center=false);
            }
        }            


        // middengat
        translate([hele_breedte/2+breedte_ruimte_tussen_2_motoren-lengte_ruimte_tussen_2_motoren/2,-diepte/2-lengte_ruimte_tussen_2_motoren/2,-1]){
            rotate([0,0,0]){
                cylinder(h=7, r=2, $fn=100, center=false);
            }
        }           

        // schroefgaten
        translate([hele_breedte+hele_breedte/2+breedte_ruimte_tussen_2_motoren-2,diepte/2-3,-1]){
            rotate([0,0,0]){
                cylinder(h=7, r=1.2, $fn=100, center=false);
            }
        }           
        translate([hele_breedte+hele_breedte/2+breedte_ruimte_tussen_2_motoren-lengte_ruimte_tussen_2_motoren,-diepte-2-diepte/2+3,-1]){
            rotate([0,0,0]){
                cylinder(h=7, r=1.2, $fn=100, center=false);
            }
        }           

        translate([-hele_breedte/2+2,diepte/2-3,-1]){
            rotate([0,0,0]){
                cylinder(h=7, r=1.2, $fn=100, center=false);
            }
        }           
        translate([-hele_breedte/2+2,-diepte-2-diepte/2+3,-1]){
            rotate([0,0,0]){
                cylinder(h=7, r=1.2, $fn=100, center=false);
            }
        }       
    
        
        translate([-20.25,-19.5,28]){
            rotate([180,0,0]){      
/*
                // schuifcontact gaten
                translate([-9.5+75/2,31,17.5]){
                    rotate([0,0,90]){
                        include <grafiethouder-reverse.scad>;                
                    }
                }  
                translate([+9.5+75/2,31,17.5]){
                    rotate([0,0,90]){
                        include <grafiethouder-reverse.scad>;                
                    }
                }  

                // schuifcontact gaten horizontale as
                translate([4,-10+5,6.5]){
                    rotate([0,0,0]){
                        include <grafiethouder-reverse.scad>;                
                    }
                }  
*/
                // as gaten voor schuif assen        
                translate([-9.5+75/2,50,17.5]){
                    rotate([90,0,0]){
                        cylinder(h=200, r=4.1, $fn=100, center=false);
                    }
                }
                translate([9.5+75/2,50,17.5]){
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
                translate([-9.5+75/2,20,17.5]){
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
                translate([+9.5+75/2,20,17.5]){
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
