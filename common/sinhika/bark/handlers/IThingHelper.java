package sinhika.bark.handlers;

public interface IThingHelper
{

   
    public abstract void init(); // end init

    public abstract String getName(int index);

    public abstract String getCapName(int index);

    public abstract String getTypeName(int index);

    public abstract int getItemID(int index);

    public abstract void setItemID(int index, int id);

    public abstract String getLocalizationTag(int index);

    public abstract String getTexture(int index);
    
    public abstract int size();

}
