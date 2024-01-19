package jeux;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordEnumeration;

public class HighScoreManager {

    private static final String RECORD_STORE_NAME = "HighScores";

    public static void saveHighScore(int score) {
        try {
            RecordStore recordStore = RecordStore.openRecordStore(RECORD_STORE_NAME, true);

            byte[] scoreBytes = Integer.toString(score).getBytes();

            int recordId = recordStore.addRecord(scoreBytes, 0, scoreBytes.length);

            recordStore.closeRecordStore();
        } catch (RecordStoreException e) {
            e.printStackTrace();
        }
    }

    public static int getHighScore() {
        int highScore = 0;

        try {
            RecordStore recordStore = RecordStore.openRecordStore(RECORD_STORE_NAME, true);

            if (recordStore.getNumRecords() > 0) {
                RecordEnumeration enumeration = recordStore.enumerateRecords(null, null, false);

                if (enumeration.hasNextElement()) {
                    int recordId = enumeration.nextRecordId();

                    byte[] data = recordStore.getRecord(recordId);

                    String scoreString = new String(data);
                    highScore = Integer.parseInt(scoreString);
                }
            }

            recordStore.closeRecordStore();
        } catch (RecordStoreException e){
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return highScore;
    }
}
