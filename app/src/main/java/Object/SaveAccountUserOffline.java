package Object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SaveAccountUserOffline")
public class SaveAccountUserOffline {
        @PrimaryKey(autoGenerate = true)
        private int Id;
        private int Uid;
        private String UserName;
        private String Password;

    public SaveAccountUserOffline( int uid, String userName, String password) {
        Uid = uid;
        UserName = userName;
        Password = password;
    }

    public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }

        public int getUid() {
            return Uid;
        }

        public void setUid(int uid) {
            Uid = uid;
        }

        public SaveAccountUserOffline() {
        }
    }


