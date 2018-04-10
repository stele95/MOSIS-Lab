package rs.elfak.mosis.stele.myplaces.Classes;

import java.util.ArrayList;

public class MyPlacesData {

    private ArrayList<MyPlace> myPlaces;

    public MyPlacesData() {
        myPlaces=new ArrayList<>();

        myPlaces.add(new MyPlace("Place A", ""));
        myPlaces.add(new MyPlace("Place B", ""));
        myPlaces.add(new MyPlace("Place C", ""));
        myPlaces.add(new MyPlace("Place D", ""));
        myPlaces.add(new MyPlace("Place E", ""));
        myPlaces.add(new MyPlace("Place F", ""));
    }

    private static class SingletonHolder{
        public static final MyPlacesData instance = new MyPlacesData();
    }

    public static MyPlacesData getInstance(){
        return SingletonHolder.instance;
    }

    public ArrayList<MyPlace> getMyPlaces() {
        return myPlaces;
    }

    public void addNewPlace(MyPlace place){
        myPlaces.add(place);
    }

    public MyPlace getPlace(int index)
    {
        return myPlaces.get(index);
    }

    public void deletePlace(int index){
        myPlaces.remove(index);
    }
}
