breedte=42.3;
lengte=breedte;
diepte=39;
afstandTussenGaten=31;
halveBreedte=breedte/2;
halveLengte=lengte/2;
diameterMiddenring=22;
dikteMiddenring=2;
asLengte=22;
asDiameter=5;
schroefgatDiameter=4;
halveAfstandTussenGaten=afstandTussenGaten/2;
diameterKettingwiel=42;
dikteKettingwiel=22;
ruimteTussenMotorEnKettingwiel=6;
translate([-breedte/2,-lengte/2,-diepte]){
    difference(){
        union(){
            translate([0,0,0]){
               cube([breedte,lengte,diepte], center=false);
            }
            translate([halveBreedte,halveLengte,0]){
                rotate([0,0,0]){
                    cylinder(h=diepte+dikteMiddenring+asLengte, r=asDiameter/2, $fn=20, center=false);
                }
            }
            translate([halveBreedte,halveLengte,0]){
                rotate([0,0,0]){
                    cylinder(h=diepte+dikteMiddenring, r=diameterMiddenring/2, $fn=20, center=false);
                }
            }

            translate([halveBreedte,halveLengte,diepte+ruimteTussenMotorEnKettingwiel]){
                rotate([0,0,0]){
                    cylinder(h=dikteKettingwiel, r=diameterKettingwiel/2, $fn=20, center=false);
                }
            }


     
        }
        union() {

            translate([halveBreedte-halveAfstandTussenGaten,halveLengte-halveAfstandTussenGaten,0]){
                rotate([0,0,0]){
                    cylinder(h=diepte+1, r=schroefgatDiameter/2, $fn=20, center=false);
                }
            }
            translate([halveBreedte-halveAfstandTussenGaten,halveLengte+halveAfstandTussenGaten,0]){
                rotate([0,0,0]){
                    cylinder(h=diepte+1, r=schroefgatDiameter/2, $fn=20, center=false);
                }
            }
            translate([halveBreedte+halveAfstandTussenGaten,halveLengte-halveAfstandTussenGaten,0]){
                rotate([0,0,0]){
                    cylinder(h=diepte+1, r=schroefgatDiameter/2, $fn=20, center=false);
                }
            }
            translate([halveBreedte+halveAfstandTussenGaten,halveLengte+halveAfstandTussenGaten,0]){
                rotate([0,0,0]){
                    cylinder(h=diepte+1, r=schroefgatDiameter/2, $fn=20, center=false);
                }
            }
        }
    }
}
