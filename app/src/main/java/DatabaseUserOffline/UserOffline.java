package DatabaseUserOffline;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import Object.SaveAccountUserOffline;
@Dao
public interface UserOffline {
        @Insert
        void InsertAccount(SaveAccountUserOffline saveAccountUserOffline);
        @Query("SELECT*FROM SaveAccountUserOffline")
        List<SaveAccountUserOffline> GetListSavePhone();
        @Query("DELETE FROM SaveAccountUserOffline")
        void DeleteUserSaveOffline();


    }


