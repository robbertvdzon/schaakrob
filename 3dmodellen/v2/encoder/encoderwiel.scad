 diameter = 26;
 binnen_diameter_gaten = 12;
 buiten_diameter_gaten = 23;
 gat_dikte=1.3;
 diepte=2;
 aantal_gaten=10;
 buitenste_gaten_diameter = 2.5;
 difference(){
   union(){
       
        translate([0,0,0]){
            rotate([90,0,00]){
              cylinder(h=diepte, r=diameter/2, $fn=100, center=false);
            }
        }          


        

    }
    union() {

        translate([0,1,0]){
            rotate([90,0,0]){
              cylinder(h=99, r=1.5, $fn=100, center=false);
            }
        }  

        //gaten
        translate([0,0,0]){
            rotate([90,0,0]){
                for (hoek =[0:360/aantal_gaten:360])
                rotate([0,0,hoek]){
                    translate([-gat_dikte/2,0,-2]){
                        rotate([0,0,0]){
                            translate([0,binnen_diameter_gaten/2+buitenste_gaten_diameter/2,0]){
                                cylinder(h=99, r=buitenste_gaten_diameter/2, $fn=100, center=false);
                                
                            }
                            translate([-buitenste_gaten_diameter/2,binnen_diameter_gaten/2+buitenste_gaten_diameter/2,0]){
                                cube([buitenste_gaten_diameter,(buiten_diameter_gaten-binnen_diameter_gaten)/2+2,diepte+3], center=false);
                                
                            }
                        }
                    }
                }
            }
        }


    }
}
