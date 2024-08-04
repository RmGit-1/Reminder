package Reminder.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Savedata {
    
    private File datafile;
    private BufferedReader reader;
    private BufferedWriter writer;

    Savedata(File datafile) {
        this.datafile = datafile;
    }

    public void saveSchedule(ScheduleData data) {
        String str = String.format("%d,%s,%s", data.getTime(), data.title, data.detail);
        try {
            writer = new BufferedWriter(new FileWriter(datafile, true));
            writer.write(str);
            writer.newLine();
            writer.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    public void saveSchedules(ArrayList<ScheduleData> data) {
        try {
            writer = new BufferedWriter(new FileWriter(datafile, true));
            for (ScheduleData d: data) {
                String str = String.format("%d,%s,%s", d.getTime(), d.title, d.detail);
                writer.write(str);
                writer.newLine();
            }
            writer.close();
        } catch(IOException ie) {
            ie.printStackTrace();
        }
    }

    public ArrayList<ScheduleData> loadSchedules() {
        ArrayList<ScheduleData> data = new ArrayList<>();
        String in;
        try {
            reader = new BufferedReader(new FileReader(datafile));
            while ((in = reader.readLine()) != null) {
                String[] array = in.split(",");
                ScheduleData d = new ScheduleData(
                    new Date(Long.parseLong(array[0])),
                    array[1],
                    array[2]
                );
                data.add(d);
            }
            reader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public void removeSchedule(ScheduleData data) {
        try {
            reader = new BufferedReader(new FileReader(datafile));
            String out = "";
            String str;
            while ((str = reader.readLine()) != null) {
                String[] array = str.split(",");
                if (!(array[0].equals(String.valueOf(data.getTime())) && 
                    array[1].equals(data.title) && 
                    array[2].equals(data.detail))
                ) {
                    out += str + "\n";
                }
            }
            reader.close();

            writer = new BufferedWriter(new FileWriter(datafile));
            writer.write(out);
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
