package edu.sdccd.cisc191.template;

/**
 * Author Nicholas Hilaire
 *
 *
 * References: "Bro code: Java: Inheritance" https://www.youtube.com/watch?v=Zs342ePFvRI
 */
//inherited by 3 other classes
public abstract class Unit
{
    //All set to not final but they all had setters and never used anywhere in the code
    //prevent errors
    //final makes it so it dosent change anywhere else in the code
    private final String unitName;
    private final String unitType;
    private final  String specialization;
    private final int price;
    private final int armor;
    private final int health;
    private final int sightRange;
    private final double unseenRange;
    private final int speed;
    private final int weight;
    private final String abilities;


    //  Constructor to Initialize the private objects in the Unit class.
    public Unit(String unitName, String unitType, String specialization, int price, int armor,
                int health, int sightRange, double unseenRange, int speed,
                int weight, String abilities)

    {
        this.unitName = unitName;
        this.unitType = unitType;
        this.specialization = specialization;
        this.price = price;
        this.armor = armor;
        this.health = health;
        this.sightRange = sightRange;
        this.unseenRange = unseenRange;
        this.speed = speed;
        this.weight = weight;
        this.abilities = abilities;
    }

        //Setter so that the code get the data in the CSV file and translate it to code
        public String getUnitName()
        {
            return unitName;
        }

        public void setUnitName(String unitName)
        {
            this.unitName = unitName;
        }

        public String getUnitType()
        {
            return unitType;
        }

        public void setUnitType(String unitType)
        {
            this.unitType = unitType;
        }

        public String getSpecialization()
        {
            return specialization;
        }

        public void setSpecialization(String specialization)
        {
            this.specialization = specialization;
        }

        public int getPrice()
        {
            return price;
        }

        public void setPrice(int price)
        {
            this.price = price;
        }

        public int getArmor()
        {
            return armor;
        }
        public void setArmor(int armor)
        {
            this.armor = armor;
        }

        public int getHealth()
        {
            return health;
        }
        public void setHealth(int health)
        {
            this.health = health;
        }

        public int getSightRange()
        {
            return sightRange;
        }

        public void setSightRange(int sightRange)
        {
            this.sightRange = sightRange;
        }

        public double getUnseenRange()
        {
            return unseenRange;
        }
        public void setUnseenRange(double unseenRange)
        {
            this.unseenRange = unseenRange;
        }

        public int getSpeed()
        {
            return speed;
        }
        public void setSpeed(int speed)
        {
            this.speed = speed;
        }

        public int getWeight()
        {
            return weight;
        }
        public void setWeight(int weight)
        {
            this.weight = weight;
        }

        public String getAbilities()
        {
            return abilities;
        }
        public void setAbilities(String abilities)
        {
            this.abilities = abilities;
        }


        // Takes the previous get Methods and translates the data into the specific unit stat.
            @Override
            public String toString()
            {
                return "Unit{" +
                        "unitName='" + unitName + '\'' +
                        ", unitType='" + unitType + '\'' +
                        ", specialization='" + specialization + '\'' +
                        ", price=" + price +
                        ", armor=" + armor +
                        ", health=" + health +
                        ", sightRange=" + sightRange +
                        ", unseenRange=" + unseenRange +
                        ", speed=" + speed +
                        ", weight=" + weight +
                        ", abilities='" + abilities + '\'' +
                        '}';
            }

}

