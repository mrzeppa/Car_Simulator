package sample;

public class Car {

    private double speed;
    private double rounds;
    private boolean isStarted;
    private char gear;
    private double maxRounds;
    private double accelerateDownDelay = 0.9;
    private double minRounds = 1131;


    public Car() {
    }



    public Car(double speed, double rounds, boolean isStarted, char gear, double maxRounds) {
        this.speed = speed;
        this.rounds = rounds;
        this.isStarted = isStarted;
        this.gear = gear;
        this.maxRounds = maxRounds;
    }

    public double getSpeed() {
        return speed;
    }

    public double getMaxRounds() {
        return maxRounds;
    }

    public void setMaxRounds(double maxRounds) {
        this.maxRounds = maxRounds;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getRounds() {
        return rounds;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public char getGear() {
        return gear;
    }

    public void setRounds(double rounds) {
        this.rounds = rounds;
    }

    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public void setGear(char gear) {
        this.gear = gear;
    }

    public double getAccelerateDownDelay() {
        return accelerateDownDelay;
    }

    public void accelerateDown(){
        if (getSpeed() > 0) {
            setSpeed(getSpeed() - (1 * getAccelerateDownDelay()));
            double engineSpeed = ((getSpeed() / (maxGearSpeed(getGear()))) * maxRounds) + minRounds;
            if(engineSpeed < 0)
                engineSpeed = minRounds;
            setRounds(engineSpeed);
        }

    }
    public void accelerateUp(){
        if(getGear() != 'N') {
            setSpeed(getSpeed() + (1 * getDelay(getGear())));
            double engineSpeed = ((getSpeed() / (maxGearSpeed(getGear()))) * maxRounds) + minRounds;
            if(engineSpeed < 0)
                engineSpeed = minRounds;
            setRounds(engineSpeed);
        }
    }


    public void gearUp(){
        if(getGear() != '6'){
            switch (getGear()){
                case 'R':
                    setGear('N');
                    break;
                case 'N':
                    setGear('1');
                    break;
                case '1':
                    setGear('2');
                    break;
                case '2':
                    setGear('3');
                    break;
                case '3':
                    setGear('4');
                    break;
                case '4':
                    setGear('5');

                    break;
                case '5':
                    setGear('6');
                    break;
            }
        }
    }


    public void gearDown(){
        if(getGear() != 'R'){
            switch (getGear()){
                case '6':
                    setGear('5');
                    break;
                case '5':
                    setGear('4');
                    break;
                case '4':
                    setGear('3');
                    break;
                case '3':
                    setGear('2');
                    break;
                case '2':
                    setGear('1');
                    break;
                case '1':
                    setGear('N');
                    break;
                case 'N':
                    if(getSpeed() == 0)
                        setGear('R');
                    break;
            }
        }
    }

    public double getDelay(char gear){
        double delay = 1;
        switch(getGear()){
            case '6':
                delay = 0.12;
                break;
            case '5':
                delay = 0.22;
                break;
            case '4':
                delay = 0.32;
                break;
            case '3':
                delay = 0.42;
                break;
            case '2':
                delay = 0.52;
                break;
            case '1':
                delay = 0.62;
                break;
            case 'N':
                delay = 0;
                break;
            case 'R':
                delay = 0.32;
                break;
        }

        return delay;
    }

    public double maxGearSpeed(char gear){
        int maxSpeed = 1;
        switch(gear){
            case '1':
                maxSpeed = 50;
                break;
            case '2':
                maxSpeed = 100;
                break;
            case '3':
                maxSpeed = 150;
                break;
            case '4':
                maxSpeed = 200;
                break;
            case '5':
                maxSpeed = 250;
                break;
            case '6':
                maxSpeed = 300;
                break;

        }
        return maxSpeed;
    }


}