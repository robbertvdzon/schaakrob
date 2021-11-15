
 difference(){
   union(){
       

        translate([0,0,0]){
            rotate([90,0,00]){
              cylinder(h=20.5-3-3, r=5, $fn=100, center=false);
            }
        }          


        

    }
    union() {

        translate([0,1,0]){
            rotate([90,0,0]){
              cylinder(h=99, r=2, $fn=100, center=false);
            }
        }  
    }
}
