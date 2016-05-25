package com.hiteware.olympics;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created on 5/22/16.
 */
public class averagePerHourTest {

    @Test
    public void canAverageGBPerHourGivenGB() {
        Averager averager = new Averager();
        float average = averager.calculate("1:00:00", "1.00 GB", "FMHD");
        assertThat(average, equalTo((float) 1.0));
    }

    @Test
    public void canAverageMultipleHourGivenGB() {
        Averager averager = new Averager();
        float average = averager.calculate("3:00:00", "2.5 GB", "RVLTHD");
        assertThat(average, equalTo((float) (2.5/3.0)));
    }

    @Test
    public void canAverageMultipleMinutesGivenGB() {
        Averager averager = new Averager();
        float average = averager.calculate("2:15:00", "3.4 GB", "MTVLIVE");
        assertThat(average, equalTo((float) (3.4/(2.0+15.0/60.0))));
    }

    @Test
    public void canAverageMultipleSecondsGivenGB() {
        Averager averager = new Averager();
        float average = averager.calculate("0:23:42", "0.98 GB", "TCMHD");
        float time = (float) (23.0/60.0+42.0/(60.0*60.0));
        assertThat(average, equalTo((float) (0.98/time)));
    }

    @Test
    public void canAverageGBPerHourGivenMB() {
        Averager averager = new Averager();
        float average = averager.calculate("1:00:00", "728 MB", "LMNHD");
        assertThat(average, equalTo((float) (728.0 / 1024.0)));
    }

    @Test
    public void canAverageMultipleHourGivenMB() {
        Averager averager = new Averager();
        float average = averager.calculate("2:00:00", "500 MB", "ESPNHD");
        assertThat(average, equalTo((float) ((500.0/ 1024.0)/ 2.0)));
    }


    @Test
    public void canAverageMultipleMinutesGivenMB() {
        Averager averager = new Averager();
        float average = averager.calculate("2:53:00", "333 MB", "SUNDHD");
        float fb = (float) (333.0/1024.0);
        float time = (float) (2.0 + 53.0/60.0);
        assertThat(average, equalTo(fb/ time));
    }

    @Test
    public void canAverageMultipleSecondsGivenMB() {
        Averager averager = new Averager();
        float average = averager.calculate("2:53:26", "444 MB", "IFCHD");
        float fb = (float) (444.0/1024.0);
        float time = (float) (2.0 + 53.0/60.0+ 26/(60.0*60.0));
        assertThat(average, equalTo(fb/ time));
    }

    @Test
    public void canAverageAndRecall() {
        Averager averager = new Averager();
        averager.calculate("1:00:00", "1.0 GB","AMCHD");
        averager.calculate("2:00:00", "1.0 GB","AMCHD");
        assertThat(averager.retrieve("AMCHD"), equalTo((float) 0.75));
    }

    @Test
    public void canAverageAndRecallForMultipleChannels() {
        Averager averager = new Averager();
        averager.calculate("1:00:00", "1.0 GB", "AMCHD");
        averager.calculate("2:00:00", "1.0 GB", "IFCHD");
        assertThat(averager.retrieve("AMCHD"), equalTo((float) 1.0));
        assertThat(averager.retrieve("IFCHD"), equalTo((float) 0.5));
    }

    @Test
    public void canAverageMultipleChannelsAndReturnAverageOfAllForChannelsNotRecorded() {
        Averager averager = new Averager();
        averager.calculate("1:00:00","1.0 GB", "AMCHD");
        averager.calculate("2:00:00","1.0 GB", "TWO");
        averager.calculate("3:00:00","1.0 GB", "THREE");
        assertThat(averager.retrieve("ANOTHER"), equalTo(((float) ((1.0+0.5+1.0/3.0)/3.0))));
    }
}
