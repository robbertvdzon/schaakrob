
difference(){
    
    breedte=56.5;
    lengte=breedte;
    diepte=56.5;
    afstandTussenGaten=47;
    halveBreedte=breedte/2;
    halveLengte=lengte/2;
    halveAfstandTussenGaten=afstandTussenGaten/2;
    
	union(){
        translate([0,0,0]){
           cube([breedte,lengte,56.5], center=false);
        }
        translate([halveBreedte,halveLengte,0]){
            rotate([0,0,0]){
                cylinder(h=diepte+25.5, r=6.5/2, $fn=20, center=false);
            }
        }
        translate([halveBreedte,halveLengte,0]){
            rotate([0,0,0]){
                cylinder(h=diepte+1, r=20, $fn=20, center=false);
            }
        }

 
	}
	union() {

        translate([halveBreedte-halveAfstandTussenGaten,halveLengte-halveAfstandTussenGaten,0]){
            rotate([0,0,0]){
                cylinder(h=diepte+1, r=2.5, $fn=20, center=false);
            }
        }
        translate([halveBreedte-halveAfstandTussenGaten,halveLengte+halveAfstandTussenGaten,0]){
            rotate([0,0,0]){
                cylinder(h=diepte+1, r=2.5, $fn=20, center=false);
            }
        }
        translate([halveBreedte+halveAfstandTussenGaten,halveLengte-halveAfstandTussenGaten,0]){
            rotate([0,0,0]){
                cylinder(h=diepte+1, r=2.5, $fn=20, center=false);
            }
        }
        translate([halveBreedte+halveAfstandTussenGaten,halveLengte+halveAfstandTussenGaten,0]){
            rotate([0,0,0]){
                cylinder(h=diepte+1, r=2.5, $fn=20, center=false);
            }
        }


        
        translate([breedte,lengte,-1]){
            rotate([0,0,0]){
                cylinder(h=diepte-4, r=9, $fn=20, center=false);
            }
        }
        translate([breedte,0,-1]){
            rotate([0,0,0]){
                cylinder(h=diepte-4, r=9, $fn=20, center=false);
            }
        }
        translate([0,lengte,-1]){
            rotate([0,0,0]){
                cylinder(h=diepte-4, r=9, $fn=20, center=false);
            }
        }
        translate([0,0,-1]){
            rotate([0,0,0]){
                cylinder(h=diepte-4, r=9, $fn=20, center=false);
            }
        }
        
        
	}
}
