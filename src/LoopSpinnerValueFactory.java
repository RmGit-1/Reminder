package Reminder.src;

import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;

public class LoopSpinnerValueFactory extends SpinnerValueFactory<Integer> {

    private final int min;
    private final int max;
    private final int stepValue;

    LoopSpinnerValueFactory(int min, int max, int initValue) {
        this.min = min;
        this.max = max;
        this.stepValue = 1;
        setValue(initValue);

        this.setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer value) {
                return value.toString();
            }

            @Override
            public Integer fromString(String value) {
                return Integer.parseInt(value);
            }
        });
    }

    LoopSpinnerValueFactory(int min, int max, int initValue, int stepValue) {
        this.min = min;
        this.max = max;
        this.stepValue = stepValue;
        setValue(initValue);

        this.setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer value) {
                return value.toString();
            }

            @Override
            public Integer fromString(String value) {
                return Integer.parseInt(value);
            }
        });
    }

    @Override
    public void increment(int steps) {
        int step = ((stepValue == 1) ? steps : stepValue);
        int value = getValue() + step;
        if (value > max) {
            value = min + (value - max - step);
        }
        setValue(value);
    }

    @Override
    public void decrement(int steps) {
        int step = ((stepValue == 1) ? steps : stepValue);
        int value = getValue() - step;
        if (value < min) {
            value = max - (min - value - step);
        }
        setValue(value);
    }

    public int getMax() {
        return max;
    }
}
