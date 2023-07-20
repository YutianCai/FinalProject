package algonquin.cst2335.finalproject.trivia;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Grade {
    @ColumnInfo(name="username")
    String username;

    @ColumnInfo(name="grade")
    int grade;

    @ColumnInfo(name = "timesent")
    String timesent;

    @ColumnInfo(name="category")
    String category;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name="id")
    public long id;

    Grade(String u,int g,String t,String c){
        username = u;
        grade = g;
        timesent = t;
        category = c;
    }
    Grade(){};

    public String getUsername(){
        return username;
    }
    public String getTimesent(){
        return timesent;
    }
    public int getGrade(){
        return grade;
    }

    public String getCategory() {
        return category;
    }

    public void setId(long id) {
        this.id = id;
    }

}
