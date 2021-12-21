breedte = 22.7;
hele_breedte = 32.5;
diepte = 27;
dikte_rand = 2.5;
marge = 0.2;

difference(){
	union(){
        
        translate([-(hele_breedte+marge*2)/2,-(diepte+marge*2)/2,0]){

            translate([(hele_breedte-breedte)/2+3,0,0]){
                cube([breedte+marge*2-6,diepte+marge*2+10,12], center=false);
            }         

            translate([(hele_breedte-breedte)/2,0,0]){
                cube([breedte+marge*2,diepte+marge*2+0,12], center=false);
            }         
            translate([0,15.5,0]){
                cube([hele_breedte+marge*2,dikte_rand+marge*2,12], center=false);
            }         
        }


	}
	union() {

	}
}
