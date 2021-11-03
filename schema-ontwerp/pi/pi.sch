EESchema Schematic File Version 4
EELAYER 30 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L Connector:Raspberry_Pi_2_3 J?
U 1 1 618326F3
P 2550 2150
F 0 "J?" H 2550 3631 50  0000 C CNN
F 1 "Raspberry_Pi_2_3" H 2550 3540 50  0000 C CNN
F 2 "" H 2550 2150 50  0001 C CNN
F 3 "https://www.raspberrypi.org/documentation/hardware/raspberrypi/schematics/rpi_SCH_3bplus_1p0_reduced.pdf" H 2550 2150 50  0001 C CNN
	1    2550 2150
	1    0    0    -1  
$EndComp
$Comp
L MCU_Module:Arduino_Nano_v3.x A?
U 1 1 61833D01
P 5450 2150
F 0 "A?" H 5450 1061 50  0000 C CNN
F 1 "Arduino_Nano_v3.x" H 5450 970 50  0000 C CNN
F 2 "Module:Arduino_Nano" H 5450 2150 50  0001 C CIN
F 3 "http://www.mouser.com/pdfdocs/Gravitech_Arduino_Nano3_0.pdf" H 5450 2150 50  0001 C CNN
	1    5450 2150
	1    0    0    -1  
$EndComp
$Comp
L RF:NRF24L01 U?
U 1 1 618354CB
P 8250 2200
F 0 "U?" H 8250 3181 50  0000 C CNN
F 1 "NRF24L01" H 8250 3090 50  0000 C CNN
F 2 "Package_DFN_QFN:QFN-20-1EP_4x4mm_P0.5mm_EP2.5x2.5mm" H 8450 3000 50  0001 L CIN
F 3 "http://www.nordicsemi.com/eng/content/download/2730/34105/file/nRF24L01_Product_Specification_v2_0.pdf" H 8250 2300 50  0001 C CNN
	1    8250 2200
	1    0    0    -1  
$EndComp
$Comp
L Regulator_Linear:LT1083-3.3 3.3Vregulator
U 1 1 61835FCA
P 5650 6400
F 0 "3.3Vregulator" H 5650 6549 50  0000 C CNN
F 1 "LT1083-3.3" H 5650 6640 50  0000 C CNN
F 2 "" H 5650 6650 50  0001 C CIN
F 3 "https://www.analog.com/media/en/technical-documentation/data-sheets/1083ffe.pdf" H 5650 6400 50  0001 C CNN
	1    5650 6400
	-1   0    0    1   
$EndComp
$Comp
L Connector:Conn_01x02_Male J?
U 1 1 61836BBC
P 9050 4100
F 0 "J?" H 9158 4281 50  0000 C CNN
F 1 "Power connector" H 9700 4100 50  0000 C CNN
F 2 "" H 9050 4100 50  0001 C CNN
F 3 "~" H 9050 4100 50  0001 C CNN
	1    9050 4100
	1    0    0    -1  
$EndComp
$Comp
L Connector:Conn_01x04_Male J?
U 1 1 618372FE
P 9050 4800
F 0 "J?" H 9158 5081 50  0000 C CNN
F 1 "Naar display" H 9600 4800 50  0000 C CNN
F 2 "" H 9050 4800 50  0001 C CNN
F 3 "~" H 9050 4800 50  0001 C CNN
	1    9050 4800
	1    0    0    -1  
$EndComp
$Comp
L Connector:Conn_01x04_Male J?
U 1 1 618377F6
P 9050 5500
F 0 "J?" H 9158 5781 50  0000 C CNN
F 1 "Naar toetsen" H 9600 5450 50  0000 C CNN
F 2 "" H 9050 5500 50  0001 C CNN
F 3 "~" H 9050 5500 50  0001 C CNN
	1    9050 5500
	1    0    0    -1  
$EndComp
Wire Wire Line
	9050 4100 5650 4100
Wire Wire Line
	5650 4100 5650 4700
Wire Wire Line
	2850 3450 2850 3850
Wire Wire Line
	2850 3850 5550 3850
Wire Wire Line
	5650 3850 5650 4100
Connection ~ 5650 4100
Wire Wire Line
	5550 3150 5550 3850
Connection ~ 5550 3850
Wire Wire Line
	5550 3850 5650 3850
Wire Wire Line
	9050 4700 5650 4700
Connection ~ 5650 4700
Wire Wire Line
	9250 4200 7450 4200
Wire Wire Line
	7450 4200 7450 4800
Wire Wire Line
	9050 4800 7450 4800
Connection ~ 7450 4800
Wire Wire Line
	9050 4900 4200 4900
Wire Wire Line
	4200 4900 4200 1550
Wire Wire Line
	4200 1550 3800 1550
Wire Wire Line
	3350 1650 3650 1650
Wire Wire Line
	4100 1650 4100 5000
Wire Wire Line
	4100 5000 9250 5000
Wire Wire Line
	5650 4700 5650 6100
Wire Wire Line
	7450 6400 5950 6400
Wire Wire Line
	7450 4800 7450 6400
Wire Wire Line
	1100 2350 1750 2350
Wire Wire Line
	9050 5700 1100 5700
Wire Wire Line
	1100 2350 1100 5700
Wire Wire Line
	9050 5600 1200 5600
Wire Wire Line
	1200 5600 1200 2450
Wire Wire Line
	1200 2450 1750 2450
Wire Wire Line
	9050 5500 1300 5500
Wire Wire Line
	1300 5500 1300 2550
Wire Wire Line
	1300 2550 1750 2550
Wire Wire Line
	9050 5400 1400 5400
Wire Wire Line
	1400 5400 1400 2850
Wire Wire Line
	1400 2850 1750 2850
Wire Wire Line
	5950 2550 6350 2550
Wire Wire Line
	6350 2550 6350 950 
Wire Wire Line
	6350 950  3800 950 
Wire Wire Line
	3800 950  3800 1550
Connection ~ 3800 1550
Wire Wire Line
	3800 1550 3350 1550
Wire Wire Line
	5950 2650 6500 2650
Wire Wire Line
	6500 2650 6500 850 
Wire Wire Line
	6500 850  3650 850 
Wire Wire Line
	3650 850  3650 1650
Connection ~ 3650 1650
Wire Wire Line
	3650 1650 4100 1650
Wire Wire Line
	5350 6400 5350 3500
Wire Wire Line
	5350 3500 8150 3500
Wire Wire Line
	8150 3500 8150 3000
Wire Wire Line
	7650 2200 7150 2200
Wire Wire Line
	7150 2200 7150 3700
Wire Wire Line
	7150 3700 4700 3700
Wire Wire Line
	4700 3700 4700 2250
Wire Wire Line
	4700 2250 4950 2250
Wire Wire Line
	7650 1900 6900 1900
Wire Wire Line
	6900 1900 6900 3450
Wire Wire Line
	6900 3450 4800 3450
Wire Wire Line
	4800 3450 4800 2850
Wire Wire Line
	4800 2850 4950 2850
Wire Wire Line
	7650 1800 6800 1800
Wire Wire Line
	6800 1800 6800 3250
Wire Wire Line
	6800 3250 4900 3250
Wire Wire Line
	4900 3250 4900 2750
Wire Wire Line
	4900 2750 4950 2750
Wire Wire Line
	7650 1700 7100 1700
Wire Wire Line
	7100 1700 7100 1050
Wire Wire Line
	7100 1050 4850 1050
Wire Wire Line
	4850 1050 4850 2650
Wire Wire Line
	4850 2650 4950 2650
Wire Wire Line
	7650 2000 7450 2000
Wire Wire Line
	7450 2000 7450 650 
Wire Wire Line
	7450 650  4500 650 
Wire Wire Line
	4500 2350 4950 2350
Wire Wire Line
	4500 650  4500 2350
$EndSCHEMATC
