package com.vdzon.java;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BerekenVersnelling {
  /*
  100: 65
  1000: 335
  2000: 525
  10.000 1576
  15.000 2201
   */

  static final double MAX_SNELHEID = 1000000/40; // pulsen per sec
  static final double VERSNELLINGSTIJD = 200000; // in microsec
  static final int INDEX_STEPS = 20;
  private static final int CALCULATION_PROCESSOR_TIME = 45;


  public static void main(String args[]) {
    List<String> snelheidList = getSnelheidList().stream().map(i->i.toString()).collect(Collectors.toList());
    System.out.println("static const int delayList[] = {"+String.join(",",snelheidList)+"};");
    System.out.println("static const int delayArraySize = " + snelheidList.size() + ";");
    System.out.println("static const int indexSteps = " + INDEX_STEPS + ";");


    System.out.println("10000:"+berekenTijd(10000));
    System.out.println("20000:"+berekenTijd(20000));
    Delays delays = calcDelays(10000,20000);
    System.out.println("delay 2="+delays.delay2);
    System.out.println("delay 3="+delays.delay3);

    System.out.println("10000:"+berekenTijd(10000, (int)delays.delay2, CALCULATION_PROCESSOR_TIME));
    System.out.println("20000:"+berekenTijd(20000, (int)delays.delay3, CALCULATION_PROCESSOR_TIME));


//    System.out.println("100:"+berekenTijd(100));
//    System.out.println("1000:"+berekenTijd(1000));
//    System.out.println("2000:"+berekenTijd(2000));
//    System.out.println("10000:"+berekenTijd(10000));
//    System.out.println("15000:"+berekenTijd(15000));
//
//    System.out.println("100 = "+(berekenTijd(100)-65)/100);
//    System.out.println("1000 = "+(berekenTijd(1000)-335)/1000);
//    System.out.println("2000 = "+(berekenTijd(2000)-525)/2000);
//    System.out.println("10000 = "+(berekenTijd(10000)-1565)/10000);
//    System.out.println("15000 = "+(berekenTijd(15000)-2201)/15000);
  }

  public static Delays calcDelays(int pulses1, int pulses2) {

    double tijd1 = berekenTijd(pulses1);
    double tijd2 = berekenTijd(pulses2);
    double tijd = Math.max(tijd1, tijd2);
    if (tijd<100){
      tijd = 100;
    }

    double doubleDiff1 = tijd-tijd1;
    double doubleDiff2 = tijd-tijd2;
    double sleepTijd1 = berekenTijd(pulses1, 100,0);
    double sleepTijd2 = berekenTijd(pulses2, 100,0);
    double newSleepTime1 = sleepTijd1+doubleDiff1;
    double newSleepTime2 = sleepTijd2+doubleDiff2;


    double delayFactor1 = pulses1 == 0 ? 100  : 100*newSleepTime1/sleepTijd1;
    double delayFactor2 = pulses2 == 0 ? 100  : 100*newSleepTime2/sleepTijd2;

    Delays delays = new Delays();
    delays.delay1 = delayFactor1;
    delays.delay2 = delayFactor2;
    delays.totalTime = (long)tijd;
    return delays;
  }


  public static double berekenTijd(int totalSteps) {
    return berekenTijd(totalSteps, 100, CALCULATION_PROCESSOR_TIME);
  }

  public static double berekenTijd(int totalSteps, double vertraging, int calculationProcessorTime) {
    List<Integer> snelheidList = getSnelheidList();
    long time = 0;


    long halfway = totalSteps / 2;
    int delayIndex = 0;
    int remainingDelayIndex = 0;
    int delay = 0;
    int delayArraySize = snelheidList.size();

    for (int i = 0; i < totalSteps; i++) {
      int remainingSteps = totalSteps - i;
      delayIndex = i / INDEX_STEPS;
      remainingDelayIndex = remainingSteps / INDEX_STEPS;
      if (i == 0 || i % INDEX_STEPS == 0) {
        if (i < halfway && delayIndex < delayArraySize)
          delay = snelheidList.get(delayIndex);
        if (i > halfway && remainingDelayIndex < delayArraySize)
          delay = snelheidList.get(remainingDelayIndex);
      }
      double tmpDelay = delay;
      tmpDelay*=vertraging/100;
      int tmpDelayInt = (int)tmpDelay;
      time += tmpDelayInt*2+ calculationProcessorTime;
    }
    return time/1000;
  }


  public static List<Integer> getSnelheidList() {
    List<Integer> result = new ArrayList<>();
    int count = 0;
    boolean finished = false;
    double tijd = 0.001;

    while (!finished) {
      double delay = VERSNELLINGSTIJD / (MAX_SNELHEID * tijd);
      double snelheid = 1000000 / delay;
      finished = snelheid > MAX_SNELHEID;
      tijd += (delay / 1000000);
      count++;
      if (count % INDEX_STEPS == 0) {
        result.add((int)delay);
      }
    }
    return result;

  }

}
