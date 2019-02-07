package com.blacksquare.tasksms.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    private String ID, Title, Date;
    private int priority;
    private boolean isDone;

    public Task() {
    }

    public Task(String title, String date, int priority) {
        Title = title;
        Date = date;
        this.priority = priority;
    }

    public Task(String ID, String title, String date, int priority) {
        this.ID = ID;
        Title = title;
        Date = date;
        this.priority = priority;
    }

    protected Task(Parcel in) {
        ID = in.readString();
        Title = in.readString();
        Date = in.readString();
        priority = in.readInt();
        isDone = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(Title);
        parcel.writeString(Date);
        parcel.writeInt(priority);
        parcel.writeByte((byte) (isDone ? 1 : 0));
    }
}
