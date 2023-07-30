package algonquin.cst2335.finalproject.trivia;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Grade {
    @ColumnInfo(name="username")
    String username;

    @ColumnInfo(name="grade")
    double grade;

    @ColumnInfo(name="questionNumber")
    int questionNumber;

    @ColumnInfo(name = "timesent")
    String timesent;

    @ColumnInfo(name="category")
    String category;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name="id")
    public long id;

    Grade(String u,double g,int qn, String t,String c){
        username = u;
        grade = g;
        questionNumber = qn;
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
    public double getGrade(){
        return grade;
    }

    public String getCategory() {
        return category;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

}
