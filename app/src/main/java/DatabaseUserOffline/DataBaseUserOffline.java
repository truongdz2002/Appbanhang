package DatabaseUserOffline;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Object.SaveAccountUserOffline;

@Database(entities = {SaveAccountUserOffline.class},version = 1)
public  abstract class DataBaseUserOffline extends RoomDatabase {
    private static final String DATABASE_NAME="account_user.db";
    private static DataBaseUserOffline instance;
    public static  synchronized DataBaseUserOffline getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),DataBaseUserOffline.class,DATABASE_NAME)
                      .allowMainThreadQueries()
                      .build();
        }
        return instance;
    }
    public abstract UserOffline userOffline();

}
