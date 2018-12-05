package fr.stanislas.homework2;

import android.os.Parcel;
import android.os.Parcelable;

class StansFilm implements Parcelable {
    public String filmTitle;
    public String filmDirector;
    public String filmDate;
    public String picturePath;

    StansFilm(String title, String director, String date) //constructor
    {
        filmTitle = title;
        filmDirector = director;
        filmDate = date;
        picturePath = null;
    }
    StansFilm(String title, String director, String date, String picPath) //constructor
    {
        filmTitle = title;
        filmDirector = director;
        filmDate = date;
        picturePath = picPath;
    }

    @Override
    public String toString(){return filmTitle;}//return the name of the title


    protected StansFilm(Parcel in){ //enables to read what the user enters
        filmTitle = in.readString();
        filmDirector = in.readString();
        filmDate = in.readString();
        picturePath = in.readString();
    }


    public static final Creator<StansFilm> CREATOR = new Creator<StansFilm>() { //Define the creator of the class
        @Override
        public StansFilm createFromParcel(Parcel in) { return new StansFilm(in); }

        @Override
        public StansFilm[] newArray(int size) {
            return new StansFilm[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    // Write the data
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filmTitle);
        dest.writeString(filmDirector);
        dest.writeString(filmDate);
        dest.writeString(picturePath);
    }
}
