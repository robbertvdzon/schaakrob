

module wielophanging() {
    include <wielophanging_v3.scad>;
}
module deel1() {
    include <printplaathouderv4-deel1.scad>;
}

module deel3() {
    include <printplaathouderv4-deel3.scad>;
}
module sleepcontact_boven() {
    include <schuifcontact-horizontaal.scad>;
}



union(){    
        
   translate([0,0,0]){
        rotate([0,0,0]){
            wielophanging();
        }
    }
   translate([0,0,0]){
        rotate([0,0,180]){
            wielophanging();
        }
    }
   translate([0,0,0]){
        rotate([0,0,0]){
            deel1();
        }
    }

   translate([0,0,38]){
        rotate([0,180,0]){
            deel3();
        }
    }
   translate([0,0,77]){
        rotate([0,180,180]){
            sleepcontact_boven();
        }
    }
    
        

}
