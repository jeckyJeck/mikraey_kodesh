package com.JK.mikraey_kodesh;

import java.util.ArrayList;
import java.util.List;

public class dataManage {
    private static final int ROSH_HASHANA = 0;
    private static final int YOM_HAKIPURIM = 1;
    private static final int SUCA = 2;
    private static final int ARBA_MINIM = 3;
    public static final int HANUCA = 4;
    private static final int PURIM = 5;
    private static final int PESACH = 6;
    private static final int BEN_HAMETZARIM = 7;
    private static final int SHABAT_A = 8;
    private static final int SHABAT_B = 9;
    private static final int SHMITA = 10;
    private static final int KITZURIM = 11;

    public static final int BOOKS_NUMBER	= 12;

    public static final int HASCAMOT_NUMBER = 10;//should be 1 less from the real number, because it start from 0.


    /*		    book number:            0	1	2	3	4	5	6	7	8	9   10  11  12  13  14  15  16  17  18 19  20  21  22  23  24  25  26  27  28  29  30*/
    public int[] lastChapterOfBook   = {31, 32, 40, 35, 28, 47, 35, 51, 19, 22, 54, 12};
    /*		    book number:                        0	1	2	3	4	5	6	7	8	9  10  11  12  13  14  15  16  17  18 19  20  21  22  23  24  25  26  27  28  29  30*/
    public static final int[] chapterOfNispachim = {15, 13, 15, 20, 14, 16, 11, 43, 2, 12, 28, 12};

    public int[][] partsOfBook     =   {
            {15}, /*ראש השנה*/
            {13}, /*יום הכיפורים*/
            {15}, /*סוכה*/
            {16, 20}, /*4 מינים*/
            {14},/*חנוכה*/
            {16}, /*פורים*/
            {11}, /*פסח*/
            {1,5,31,43},/*בין המצרים*/
            {2}, /*שבת א*/
            {2,12}, /*שבת ב*/
            {28}, /*שמיטה*/
            {-1}
    };
    String[][] chaptersNames = new String[BOOKS_NUMBER][70];
    public ArrayList<ArrayList<ArrayList<String>>> mchaptersNames = new ArrayList<>();
    public ArrayList<String> booksHeaders = new ArrayList<>();
    public ArrayList<ArrayList<String>> partsHeaders = new ArrayList<>();

    String[][] chaptersFiles = new String[BOOKS_NUMBER][31];

    public dataManage(){
        fillChaptersNames();

    }


    public String getURL(int book_number, int chapter_number){
        return "file:///android_asset/"+ convertBookIdToEnglishName(book_number)+"_"+ chapter_number+".html";
    }
    public String getURL(int book_number, int chapter_number, boolean getShortURL){
        return convertBookIdToEnglishName(book_number)+"_"+ chapter_number+".html";
    }

    public String getFullNameOfChapter(int book_number, int chapter_number){
        return convertBookIdToName(book_number)+": "+getChapterName(book_number,chapter_number);
    }

    public String convertAnchorIdToSection(int Id)
    {
        String[] alephbet = new String[]{"", "א", "ב", "ג", "ד", "ה", "ו", "ז", "ח", "ט","","י", "כ", "ל", "מ", "נ", "ס", "ע", "פ", "צ","", "ק", "ר", "ש", "ת"};
        int handred = Id/100;
        int ten = (Id%100)/10;
        int unit = Id%10;
        if (Id == 0)
            return "פתיחה";
        else if (ten == 1 && (unit == 5 || unit == 6))
            return alephbet[handred+20]+alephbet[9] + alephbet[unit+1];
        else
            return alephbet[handred+20]+alephbet[ten+10] + alephbet[unit];

    }

    public String convertBookIdToName(int bookId)
    {
        switch (bookId)
        {
            case HANUCA:
                return "חנוכה";
            case BEN_HAMETZARIM:
                return "ג תעניות • בין המצרים • ט באב";
            case ROSH_HASHANA:
                return "ראש השנה";
            case YOM_HAKIPURIM:
                return "יום הכיפורים";
            case PURIM:
                return "פורים";
            case SHMITA:
                return "שמיטה";
            case PESACH:
                return "פסח";
            case SUCA:
                return "סוכה";
            case ARBA_MINIM:
                return "ארבעת המינים";
            case SHABAT_A:
                return "קדושת השבת א";
            case SHABAT_B:
                return "קדושת השבת ב";
            case KITZURIM:
                return "קיצורים";
            default:
                return "לא ידוע";
        }
    }

    public String getPartName(int book, int chap){
        switch (book){
            case HANUCA:
                if (chap == 0)
                    return "הקדמה";
                else if (chap < 14)
                    return "חנוכה";
                else if (chap < lastChapterOfBook[book])
                    return "נספחים: חנוכה";
                else
                    return "חנוכה";
            case BEN_HAMETZARIM:
                if (chap == 0)
                    return "הקדמה";
                else if (chap < 5)
                    return "שלוש התעניות";
                else if (chap < 31)
                    return "בין המצרים";
                else if (chap < 43)
                    return "ט באב";
                else if (chap < lastChapterOfBook[book])
                    return "נספחים: תעניות, בין המצרים, ט באב";
                else
                    return "בין המצרים";
            case ROSH_HASHANA:
                if (chap == 0)
                    return "הקדמה";
                else if (chap < 15)
                    return "ראש השנה";
                else if (chap < lastChapterOfBook[book])
                    return "נספחים: ראש השנה";
                else
                    return "ראש השנה";
            case YOM_HAKIPURIM:
                if (chap == 0)
                    return "הקדמה";
                else if (chap < 13)
                    return "יום הכיפורים";
                else if (chap < lastChapterOfBook[book])
                    return "נספחים: יום הכיפורים";
                else
                    return "יום הכיפורים";
            case PURIM:
                if (chap == 0)
                    return "הקדמה";
                else if (chap < 16)
                    return "פורים";
                else if (chap < lastChapterOfBook[book])
                    return "נספחים: פורים";
                else
                    return "פורים";
            case SHMITA:
                if (chap == 0)
                    return "הקדמה";
                else if (chap < 28)
                    return "שמיטה";
                else if (chap < lastChapterOfBook[book])
                    return "נספחים: שמיטה";
                else
                    return "שמיטה";
            case PESACH:
                if (chap == 0)
                    return "הקדמה";
                else if (chap < 11)
                    return "פסח";
                else if (chap < lastChapterOfBook[book])
                    return "נספחים: פסח";
                else
                    return "פסח";
            case SUCA:
                if (chap == 0)
                    return "תוכן מפורט";
                if (chap == 1)
                    return "הקדמה";
                else if (chap < 15)
                    return "סוכה";
                else if (chap < lastChapterOfBook[book])
                    return "נספחים: סוכה";
                else
                    return "סוכה";
            case ARBA_MINIM:
                if (chap == 0)
                    return "הקדמה";
                if (chap == 1)
                    return "קיצור ההלכות";
                else if (chap < 16)
                    return "ד מינים";
                else if (chap < 20)
                    return "שער התפילות";
                else if (chap < lastChapterOfBook[book])
                    return "נספחים: ד מינים";
                else
                    return "ד מינים";
            case SHABAT_A:
                if (chap == 0)
                    return "הקדמה";
                if (chap == 1)
                    return "פירוט המכשירים";
                else if (chap < lastChapterOfBook[book])
                    return "נספחים: שבת א";
                else
                    return "קדושת השבת א";
            case SHABAT_B:
                if (chap == 0)
                    return "הקדמה";
                if (chap == 1)
                    return "פירוט המכשירים";
                else if (chap < 12)
                    return "מילואים שבת ב";
                else if (chap < lastChapterOfBook[book])
                    return "נספחים: שבת ב";
                else
                    return "שבת ב";
            case KITZURIM:
                switch (chap){
                    case 0:
                        return "קיצורים - הלכות ראש השנה";
                    case 1:
                        return "קיצורים - הלכות יום הכיפורים";
                    case 2:
                        return "קיצורים - הלכות סוכה";
                    case 3:
                        return "קיצורים - הלכות ארבעת המינים";
                    case 4:
                        return "קיצורים הלכות חנוכה";
                    case 5:
                        return "קיצורים - הלכות טו בשבט";
                    case 6:
                        return "קיצורים - הלכות פורים";
                    case 7:
                        return "קיצורים - הלכות פסח";
                    case 8:
                        return "קיצורים - הלכות יום טוב";
                    case 9:
                        return "קיצורים - הלכות חול המועד";
                    case 10:
                        return "קיצורים - הלכות ספירת העומר";
                    case 11:
                        return "קיצורים - הלכות שבועות";
                    case 12:
                        return "קיצורים - הלכות ג התעניות בין המצרים וט באב";
                    default:
                        return "קיצורים";
                }

            default:
                return convertBookIdToName(book);

        }
    }

    public String convertBookIdToEnglishName(int bookId)
    {
        switch (bookId)
        {
            case HANUCA:
                return "hanuca";
            case BEN_HAMETZARIM:
                return "ben_hametzarim";
            case ROSH_HASHANA:
                return "rosh_hashana";
            case YOM_HAKIPURIM:
                return "yom_hakipurim";
            case PURIM:
                return "purim";
            case SHMITA:
                return "shmita";
            case PESACH:
                return "pesach";
            case SUCA:
                return "suca";
            case ARBA_MINIM:
                return "arba_minim";
            case SHABAT_A:
                return "shabat_aa";
            case SHABAT_B:
                return "shabat_b";
            case KITZURIM:
                return "kitzurim";
            default:
                return "unknown_book_ID";
        }
    }

    public String getChapterName(int book_number, int chapter_number){
        return chaptersNames[book_number][chapter_number];
    }

    public void fillChaptersNames()
    {
        /*you can't use "}" here, it's can broke the search.*/
        /*"*c*" at start of name bold the it*/
        /*HANUCA*/
        chaptersNames[HANUCA][0] = "הקדמה";
        chaptersNames[HANUCA][1] = "פרק א: הלכות הדברים האסורים והמותרים בחנוכה";
        chaptersNames[HANUCA][2] = "פרק ב: סידור נרות החנוכה, מספרם וכמות השמן";
        chaptersNames[HANUCA][3] = "פרק ג: מקום הנחת נרות החנוכה";
        chaptersNames[HANUCA][4] = "פרק ד: זמן הדלקת נרות החנוכה";
        chaptersNames[HANUCA][5] = "פרק ה: דין השמנים, הפתילות והחנוכיות";
        chaptersNames[HANUCA][6] = "פרק ו: איסור שימוש בנרות (הנאה מאורם והדלקה מנר לנר)";
        chaptersNames[HANUCA][7] = "פרק ז: דין הדלקה עושה מצוה, וכבתה אין זקוק לה";
        chaptersNames[HANUCA][8] = "פרק ח: סדר הברכות וההדלקה";
        chaptersNames[HANUCA][9] = "פרק ט: מי חייב בנר חנוכה, ודין אורח";
        chaptersNames[HANUCA][10] = "פרק י: הדלקת נרות החנוכה בבית הכנסת, בחתונות ובכינוסים";
        chaptersNames[HANUCA][11] = "פרק יא: הלכות הדלקת נרות החנוכה בליל שבת ובכניסת שבת";
        chaptersNames[HANUCA][12] = "פרק יב: סדר התפילה בחנוכה";
        chaptersNames[HANUCA][13] = "פרק יג: קריאת התורה בחנוכה";
        chaptersNames[HANUCA][14] = "*c*"+"- חלק ב: נספחים -";
        chaptersNames[HANUCA][15] = "נספח א: תשובות לשאלות תלמידי ישיבת \"מרכז הרב\"";
        chaptersNames[HANUCA][16] = "נספח ב: מכתב הגר\"א שפירא זצ\"ל";
        chaptersNames[HANUCA][17] = "נספח ג: מכתב לג\"ר שלום משאש זצ\"ל, רבה הראשי של ירושלים";
        chaptersNames[HANUCA][18] = "נספח ד: מכתב לג\"ר חיים קנייבסקי שליט\"א";
        chaptersNames[HANUCA][19] = "נספח ה: מכתב לג\"ר חיים קנייבסקי שליט\"א";
        chaptersNames[HANUCA][20] = "נספח ו: מכתב לגר\"ש דבליצקי שליט\"א";
        chaptersNames[HANUCA][21] = "נספח ז: קובץ מהלכות חנוכה מהג\"ר משה פיינשטיין זצ\"ל";
        chaptersNames[HANUCA][22] = "נספח ח: מכתב הגר\"ש משאש זצ\"ל";
        chaptersNames[HANUCA][23] = "נספח ט: מכתב הג\"ר מרדכי אליהו זצ\"ל";
        chaptersNames[HANUCA][24] = "נספח י: מכתב הג\"ר שריה דבליצקי שליט\"א";
        chaptersNames[HANUCA][25] = "נספח יא: מכתב הג\"ר אביגדר הלוי נבנצל שליט\"א";
        chaptersNames[HANUCA][26] = "*c*"+"נספח יב: הגר\"ע יוסף זצ\"ל – קווים לדמותו";
        chaptersNames[HANUCA][27] = "נספח יג: מגילת אנטיוכוס";


        /*ben_hametzarim*/
        chaptersNames[BEN_HAMETZARIM][0] = "הקדמה";
        chaptersNames[BEN_HAMETZARIM][1] = "*c*- חלק א: הלכות שלש התעניות -";
        chaptersNames[BEN_HAMETZARIM][2] = "פרק א: איסור האכילה בשלוש התעניות והפטורים מהתענית";
        chaptersNames[BEN_HAMETZARIM][3] = "פרק ב: דיני התפילות בשלש התעניות";
        chaptersNames[BEN_HAMETZARIM][4] = "פרק ג: שאר דיני שלש התעניות";
        chaptersNames[BEN_HAMETZARIM][5] = "*c*- חלק ב: הלכות בין המצרים -";
        chaptersNames[BEN_HAMETZARIM][6] = "פרק מקדים: הלכות בין המצרים לפי סדר הזמנים";
        chaptersNames[BEN_HAMETZARIM][7] = "פרק א: איסור אכילת בשר ושתיית יין";
        chaptersNames[BEN_HAMETZARIM][8] = "פרק ב: בגד חדש - לבישתו";
        chaptersNames[BEN_HAMETZARIM][9] = "פרק ג: בגד נקי - לבישתו";
        chaptersNames[BEN_HAMETZARIM][10] = "פרק ד: בניה ונטיעה";
        chaptersNames[BEN_HAMETZARIM][11] = "פרק ה: טיולים ובילויים";
        chaptersNames[BEN_HAMETZARIM][12] = "פרק ו: כיבוס וגיהוץ";
        chaptersNames[BEN_HAMETZARIM][13] = "פרק ז: נגינה בכלי נגינה (שמיעה וניגון)";
        chaptersNames[BEN_HAMETZARIM][14] = "פרק ח: סכנה - זהירות ממנה";
        chaptersNames[BEN_HAMETZARIM][15] = "פרק ט: סירוק שיער";
        chaptersNames[BEN_HAMETZARIM][16] = "פרק י: צחצוח נעלים";
        chaptersNames[BEN_HAMETZARIM][17] = "פרק יא: צפורנים - גזיזתן";
        chaptersNames[BEN_HAMETZARIM][18] = "פרק יב: קידושין (אירוסים) ונישואים";
        chaptersNames[BEN_HAMETZARIM][19] = "פרק יג: קנית דבר חדש ומסחר";
        chaptersNames[BEN_HAMETZARIM][20] = "פרק יד: רחיצה";
        chaptersNames[BEN_HAMETZARIM][21] = "פרק טו: רצפה - שטיפתה";
        chaptersNames[BEN_HAMETZARIM][22] = "פרק טז: רקודים ומחולות";
        chaptersNames[BEN_HAMETZARIM][23] = "פרק יז: שבוע שחל בו תשעה באב, זמן חלוּתוֹ";
        chaptersNames[BEN_HAMETZARIM][24] = "פרק יח: \"שהחיינו\", ברכתה בימים אלה";
        chaptersNames[BEN_HAMETZARIM][25] = "פרק יט: שירה";
        chaptersNames[BEN_HAMETZARIM][26] = "פרק כ: תביעה בבית דין";
        chaptersNames[BEN_HAMETZARIM][27] = "פרק כא: תספורת וגילוח";
        chaptersNames[BEN_HAMETZARIM][28] = "פרק כב: תפילות והנהגות";
        chaptersNames[BEN_HAMETZARIM][29] = "פרק כג: תפירת ותיקון בגדים";
        chaptersNames[BEN_HAMETZARIM][30] = "פרק כד: תשמיש המיטה";
        chaptersNames[BEN_HAMETZARIM][31] = "*c*- חלק ג: הלכות תשעה באב -";
        chaptersNames[BEN_HAMETZARIM][32] = "פרק א: צום תשעה באב, מקורו וטעמו";
        chaptersNames[BEN_HAMETZARIM][33] = "פרק ב: דין ערב תשעה באב (יום ח' באב. כולל דיני הסעודה המפסקת)";
        chaptersNames[BEN_HAMETZARIM][34] = "פרק ג: דין ליל תשעה באב (כולל תפילת ערבית)";
        chaptersNames[BEN_HAMETZARIM][35] = "פרק ד: דין איסור אכילה ושתיה בתשעה באב";
        chaptersNames[BEN_HAMETZARIM][36] = "פרק ה: דין הפטורים מן התענית בתשעה באב";
        chaptersNames[BEN_HAMETZARIM][37] = "פרק ו: שאר איסורי היום (כולל רחיצה, סיכה, נעילת נעלים ותשמיש המיטה)";
        chaptersNames[BEN_HAMETZARIM][38] = "פרק ז: עוד מהדברים האסורים בתשעה באב";
        chaptersNames[BEN_HAMETZARIM][39] = "פרק ח: דיני תפילות היום (שחרית ומנחה) בתשעה באב";
        chaptersNames[BEN_HAMETZARIM][40] = "פרק ט: עוד מדיני תשעה באב";
        chaptersNames[BEN_HAMETZARIM][41] = "פרק י: דין תשעה באב שחל בשבת או ביום ראשון";
        chaptersNames[BEN_HAMETZARIM][42] = "פרק יא: הלכות מוצאי תשעה באב";
        chaptersNames[BEN_HAMETZARIM][43] = "*c*- נספחים -";
        chaptersNames[BEN_HAMETZARIM][44] = "נספח א: מכתב הג\"ר שלמה גורן זצ\"ל";
        chaptersNames[BEN_HAMETZARIM][45] = "נספח ב: שמיעת מוזיקה ווקאלית בימי בין המצרים";
        chaptersNames[BEN_HAMETZARIM][46] = "נספח ג: סיפורים מהשואה";
        chaptersNames[BEN_HAMETZARIM][47] = "נספח ד: קינות על השואה";
        chaptersNames[BEN_HAMETZARIM][48] = "נספח ה: קינה על חורבן גוש קטיף וצפון השומרון";
        chaptersNames[BEN_HAMETZARIM][49] = "נספח ו: מדרש אלה אזכרה (עשרת הרוגי מלכות)";
        chaptersNames[BEN_HAMETZARIM][50] = "נספח ז: מדרש נחמה - \"מנחם משיב נפשי\"";

        /*rosh_hashana*/
        chaptersNames[ROSH_HASHANA][0] = "הקדמה";
        chaptersNames[ROSH_HASHANA][1] = "פרק א: הלכות ומנהגי חודש אלול";
        chaptersNames[ROSH_HASHANA][2] = "פרק ב: הלכות ערב ראש השנה (יום כ\"ט באלול)";
        chaptersNames[ROSH_HASHANA][3] = "פרק ג: הלכות תפילת ליל ראש השנה";
        chaptersNames[ROSH_HASHANA][4] = "פרק ד: הלכות סעודת ליל ראש השנה ומנהגי הלילה";
        chaptersNames[ROSH_HASHANA][5] = "פרק ה: הלכות תפילת שחרית של ראש השנה";
        chaptersNames[ROSH_HASHANA][6] = "פרק ו: הלכות קריאת התורה בראש השנה";
        chaptersNames[ROSH_HASHANA][7] = "פרק ז: הלכות מצות תקיעת שופר וברכותיה";
        chaptersNames[ROSH_HASHANA][8] = "פרק ח: הלכות השופר";
        chaptersNames[ROSH_HASHANA][9] = "פרק ט: זמן תקיעת שופר";
        chaptersNames[ROSH_HASHANA][10] = "פרק י: מי הם החייבים בתקיעת שופר";
        chaptersNames[ROSH_HASHANA][11] = "פרק יא: דיני התקיעות";
        chaptersNames[ROSH_HASHANA][12] = "פרק יב: הלכות תפילת מוסף של ראש השנה";
        chaptersNames[ROSH_HASHANA][13] = "פרק יג: הלכות הנהגת יום ראש השנה";
        chaptersNames[ROSH_HASHANA][14] = "פרק יד: הלכות תפילת מנחה של ראש השנה, תשליך ומוצאי החג";
        chaptersNames[ROSH_HASHANA][15] = "*c*"+"- חלק ב: נספחים -";   
        chaptersNames[ROSH_HASHANA][16] = "נספח א: מכתב הג\"ר שלמה גורן זצ\"ל";
        chaptersNames[ROSH_HASHANA][17] = "נספח ב: מכתב הגר\"ש דבליצקי שליט\"א בענין שיעור מלוא לוגמיו";
        chaptersNames[ROSH_HASHANA][18] = "נספח ג: מכתב הג\"ר שלום משאש זצ\"ל";
        chaptersNames[ROSH_HASHANA][19] = "נספח ד: מכתב הגר\"ש משאש זצ\"ל";
        chaptersNames[ROSH_HASHANA][20] = "נספח ה: פסקי הגר\"מ פינשטיין זצ\"ל";
        chaptersNames[ROSH_HASHANA][21] = "נספח ו: מכתב הגר\"ח קנייבסקי שליט\"א";
        chaptersNames[ROSH_HASHANA][22] = "נספח ז: מכתב הגר\"ח קנייבסקי שליט\"א";
        chaptersNames[ROSH_HASHANA][23] = "נספח ח: בענין מי כשר להיות ש\"ץ";
        chaptersNames[ROSH_HASHANA][24] = "נספח ט: דין אבל ואונן כש\"ץ בסליחות ובימים נוראים";
        chaptersNames[ROSH_HASHANA][25] = "נספח י: איסור אכילה ושתיה לפני הסליחות ותפילת שחרית, לפי הפשט ולפי הקבלה";
        chaptersNames[ROSH_HASHANA][26] = "נספח יא: האם יש לומר את המילים \"אלקינו ואלקי אבותינו, רצה נא במנוחתנו\" בסוף הברכה הרביעית של תפילת העמידה, כשיום טוב חל בשבת";
        chaptersNames[ROSH_HASHANA][27] = "נספח יב: שאלות בעניני דודי השמש החדשים";
        chaptersNames[ROSH_HASHANA][28] = "נספח יג: תשובת הג\"ר יעקב אריאל שליט\"א";
        chaptersNames[ROSH_HASHANA][29] = "נספח יד: תשובת הג\"ר יצחק יוסף שליט\"א";
        chaptersNames[ROSH_HASHANA][30] = "נספח טו: תשובת הג\"ר דב ליאור שליט\"א";

        /*SHABAT_A*/
        chaptersNames[SHABAT_A][0] = "הקדמה";
        chaptersNames[SHABAT_A][1] = "שם המכשיר והלכותיו";
        chaptersNames[SHABAT_A][2] = "*c*" + "חלק ב: נספחים";
        chaptersNames[SHABAT_A][3] = "נספח א1: מכתב בעניני חשמל בשבת ויו\"ט שנשלח לכמה רבנים שליט\"א";
        chaptersNames[SHABAT_A][4] = "נספח א: מכתב הג\"ר יעקב אריאל שליט\"א, רבה של רמת גן";
        chaptersNames[SHABAT_A][5] = "נספח ב: מכתב הגרל\"י הלפרין שליט\"א, ראש המכון המדעי טכנולוגי להלכה, ירושת\"ו";
        chaptersNames[SHABAT_A][6] = "נספח ג: מכתב הגר\"ד ליאור שליט\"א, רבה של קרית ארבע - חברון, וראש הישיבה ";
        chaptersNames[SHABAT_A][7] = "נספח ד: מכתב הגר\"א נבנצל שליט\"א, רבה של העיר העתיקה פעיה\"ק ירושת\"ו";
        chaptersNames[SHABAT_A][8] = "נספח ה: מכתב הגרי\"י נויבירט שליט\"א";
        chaptersNames[SHABAT_A][9] = "נספח ו: מכתב הגר\"ש משאש שליט\"א, רבה של ירושלים ת\"ו";
        chaptersNames[SHABAT_A][10] = "נספח ז: מכתב הגר\"ש משאש שליט\"א, רבה של ירושלים ת\"ו";
        chaptersNames[SHABAT_A][11] = "נספח ח: מכתב הגר\"ח קנייבסקי שליט\"א";
        chaptersNames[SHABAT_A][12] = "נספח ט: תשובות הרה\"ג יצחק יוסף שליט\"א, מח\"ס ילקוט יוסף ועו\"ס";
        chaptersNames[SHABAT_A][13] = "נספח י: תשובות הגרי\"ש אלישיב שליט\"א בענין הנאה בשבת מזרם החשמל הארצי";
        chaptersNames[SHABAT_A][14] = "נספח יא: מכתב הגר\"ח קנייבסקי שליט\"א";
        chaptersNames[SHABAT_A][15] = "נספח יב: מכתב הגר\"ח קנייבסקי שליט\"א";
        chaptersNames[SHABAT_A][16] = "נספח יג: מכתב הגר\"ח קנייבסקי שליט\"א";
        chaptersNames[SHABAT_A][17] = "נספח יד: מכתב הגר\"ח קנייבסקי שליט\"א";
        chaptersNames[SHABAT_A][18] = "נספח טו: תשובות הגרי\"י נויבירט שליט\"א";

        /*YOM_HAKIPURIM*/
        chaptersNames[YOM_HAKIPURIM][0] = "הקדמה";
        chaptersNames[YOM_HAKIPURIM][1] = "פרק א: הלכות עשרת ימי תשובה";
        chaptersNames[YOM_HAKIPURIM][2] = "פרק ב: הלכות ערב יום הכיפורים (יום ט' בתשרי)";
        chaptersNames[YOM_HAKIPURIM][3] = "פרק ג: הלכות תפילות ערב יום הכיפורים";
        chaptersNames[YOM_HAKIPURIM][4] = "פרק ד: מצוות סעודת ערב יום הכיפורים";
        chaptersNames[YOM_HAKIPURIM][5] = "פרק ה: הלכות ליל יום הכיפורים";
        chaptersNames[YOM_HAKIPURIM][6] = "פרק ו: איסור אכילה ושתיה ביום הכיפורים";
        chaptersNames[YOM_HAKIPURIM][7] = "פרק ז: דין איסור מלאכה, רחיצה וסיכה ביום הכיפורים";
        chaptersNames[YOM_HAKIPURIM][8] = "פרק ח: דין איסור נעילת הסנדל ותשמיש המיטה ביום הכיפורים";
        chaptersNames[YOM_HAKIPURIM][9] = "פרק ט: דין הפטורים מהתענית ומשאר העינויים";
        chaptersNames[YOM_HAKIPURIM][10] = "פרק י: דיני הילדים ביום הכיפורים";
        chaptersNames[YOM_HAKIPURIM][11] = "פרק יא: דיני תפילות היום וברית מילה ביום הכיפורים";
        chaptersNames[YOM_HAKIPURIM][12] = "פרק יב: סדר מוצאי יום הכיפורים";
        chaptersNames[YOM_HAKIPURIM][13] = "*c*"+"- חלק ב: נספחים -";
        chaptersNames[YOM_HAKIPURIM][14] = "נספח א: מכתב הגר\"ש דבליצקי שליט\"א";
        chaptersNames[YOM_HAKIPURIM][15] = "נספח ב: מכתב לגר\"ש מן ההר זצ\"ל ולגר\"ד ליאור שליט\"א";
        chaptersNames[YOM_HAKIPURIM][16] = "נספח ב: מכתב הגר\"ד ליאור שליט\"א";
        chaptersNames[YOM_HAKIPURIM][17] = "נספח ג: מכתב הג\"ר שלמה מן-ההר זצ\"ל";
        chaptersNames[YOM_HAKIPURIM][18] = "נספח ד: מכתב הג\"ר שלום משאש זצ\"ל";
        chaptersNames[YOM_HAKIPURIM][19] = "נספח ה: מכתב הג\"ר שלום משאש זצ\"ל";
        chaptersNames[YOM_HAKIPURIM][20] = "נספח ו: מכתב הג\"ר חיים קנייבסקי שליט\"א";
        chaptersNames[YOM_HAKIPURIM][21] = "נספח ז: מכתב לג\"ר חיים קנייבסקי שליט\"א בענין שיעור כותבת הגסה ליום הכיפורים.";
        chaptersNames[YOM_HAKIPURIM][22] = "נספח ח: מכתב הגר\"ח קנייבסקי שליט\"א בענין שיעור כותבת הגסה ליום הכיפורים";
        chaptersNames[YOM_HAKIPURIM][23] = "נספח ט: מכתב לג\"ר חיים קנייבסקי שליט\"א בענין נעילת נעלי עור לילדים";
        chaptersNames[YOM_HAKIPURIM][24] = "נספח י: מכתב הגר\"א שפירא שליט\"א";
        chaptersNames[YOM_HAKIPURIM][25] = "נספח יא: מכתב הגר\"ש דבליצקי שליט\"א בענין שיעור מלוא לוגמיו";
        chaptersNames[YOM_HAKIPURIM][26] = "נספח יב: מכתב הגר\"ש משאש זצ\"ל";
        chaptersNames[YOM_HAKIPURIM][27] = "נספח יג: פסקי הגר\"מ פינשטיין זצ\"ל";
        chaptersNames[YOM_HAKIPURIM][28] = "נספח יד: מכתב הגר\"ח קנייבסקי שליט\"א";
        chaptersNames[YOM_HAKIPURIM][29] = "נספח טו: פסקי הגרי\"ש אלישיב שליט\"א";
        chaptersNames[YOM_HAKIPURIM][30] = "נספח טז: קונטרס ברכות השחר";
        chaptersNames[YOM_HAKIPURIM][31] = "נספח יז: דין מי שטעה בנוסח ברכת הדלקת הנרות של יום הכיפורים שחל בחול או בשבת";

        /*PURIM*/
        chaptersNames[PURIM][0] = "הקדמה";
        chaptersNames[PURIM][1] = "פרק א: הלכות ארבע הפרשיות";
        chaptersNames[PURIM][2] = "פרק ב: הלכות חודש אדר";
        chaptersNames[PURIM][3] = "פרק ג: הלכות תענית אסתר";
        chaptersNames[PURIM][4] = "פרק ד: חיוב קריאת המגילה וזמנה";
        chaptersNames[PURIM][5] = "פרק ה: הלכות פרזים ומוקפים";
        chaptersNames[PURIM][6] = "פרק ו: מי הם החייבים בקריאת המגילה";
        chaptersNames[PURIM][7] = "פרק ז: הלכות קריאת המגילה";
        chaptersNames[PURIM][8] = "פרק ח: קצת מדיני כשרות המגילה";
        chaptersNames[PURIM][9] = "פרק ט: דיני ברכות המגילה";
        chaptersNames[PURIM][10] = "פרק י: הלכות התפילה וקריאת התורה בפורים";
        chaptersNames[PURIM][11] = "פרק יא: הלכות מתנות לאביונים";
        chaptersNames[PURIM][12] = "פרק יב: הלכות משלוח מנות";
        chaptersNames[PURIM][13] = "פרק יג: הלכות סעודת פורים ושמחת פורים";
        chaptersNames[PURIM][14] = "פרק יד: דין עשית מלאכה ואבלות בפורים";
        chaptersNames[PURIM][15] = "פרק טו: דיני פורים משולש";
        chaptersNames[PURIM][16] = "*c*"+"חלק ב: נספחים";
        chaptersNames[PURIM][17] = "נספח א: מכתב הג\"ר שלמה זלמן אויערבאך זצ\"ל";
        chaptersNames[PURIM][18] = "נספח ב:  מפסקי הג\"ר מרדכי אליהו שליט\"א";
        chaptersNames[PURIM][19] = "נספח ג: מכתב לרבנים";
        chaptersNames[PURIM][20] = "נספח ג1: מכתב הגר\"ש גורן זצ\"ל";
        chaptersNames[PURIM][21] = "נספח ד: מכתב הג\"ר עובדיה יוסף זצ\"ל";
        chaptersNames[PURIM][22] = "נספח ה: מכתב הג\"ר דב ליאור שליט\"א";
        chaptersNames[PURIM][23] = "נספח ו:  מכתב הג\"ר שלמה מן-ההר זצ\"ל";
        chaptersNames[PURIM][24] = "נספח ז: מכתב הגר\"ש משאש זצ\"ל";
        chaptersNames[PURIM][25] = "נספח ז(1) :  פסקי הגרצ\"פ פראנק זצ\"ל";
        chaptersNames[PURIM][26] = "נספח ח: מכתב הג\"ר חיים קנייבסקי שליט\"א";
        chaptersNames[PURIM][27] = "נספח ט:  מכתב הג\"ר חיים קנייבסקי שליט\"א";
        chaptersNames[PURIM][28] = "נספח י:  פסקי הגר\"א שפירא שליט\"א";
        chaptersNames[PURIM][29] = "נספח יא: דיני בן כרך שהלך לעיר ולהיפך";
        chaptersNames[PURIM][30] = "נספח יב: קובץ מהלכות פורים מהגאון הרב משה פינשטיין זצ\"ל";
        chaptersNames[PURIM][31] = "נספח יג: מכתב הגר\"ש דבליצקי שליט\"א";
        chaptersNames[PURIM][32] = "נספח יד: מכתב הגר\"ש משאש זצ\"ל";
        chaptersNames[PURIM][33] = "נספח טו: מכתב הגר\"ח קנייבסקי שליט\"א";
        chaptersNames[PURIM][34] = "נספח טז: מכתב הגר\"ש משאש זצ\"ל";
        chaptersNames[PURIM][35] = "נספח יז: מכתב הג\"ר שלמה גורן זצ\"ל";
        chaptersNames[PURIM][36] = "נספח יח: מכתב הג\"ר שלמה גורן זצ\"ל";
        chaptersNames[PURIM][37] = "נספח יט: מכתב הגר\"ח קנייבסקי שליט\"א";
        chaptersNames[PURIM][38] = "נספח כ: מכתב מהגר\"מ אליהו שליט\"א";
        chaptersNames[PURIM][39] = "נספח כא: מכתב הגר\"ש דבליצקי שליט\"א";
        chaptersNames[PURIM][40] = "נספח כב: תשובות הגר\"א שפירא זצ\"ל";
        chaptersNames[PURIM][41] = "נספח כג: תשובות הגר\"א שפירא זצ\"ל";
        chaptersNames[PURIM][42] = "נספח כד: תשובות הגר\"א שפירא זצ\"ל";
        chaptersNames[PURIM][43] = "נספח כה: תשובות הגר\"א שפירא זצ\"ל";
        chaptersNames[PURIM][44] = "נספח כו:  תשובות הגר\"א שפירא זצ\"ל";
        chaptersNames[PURIM][45] = "נספח כז:  מכתב הגר\"ד ליאור שליט\"א";
        chaptersNames[PURIM][46] = "נספח כח: חידה";


        /*SHMITA*/
        chaptersNames[SHMITA][0] = "הקדמה";
        chaptersNames[SHMITA][1] = "פרק א: פתיחה להלכות שנת השמיטה";
        chaptersNames[SHMITA][2] = "פרק ב: ההכנות לשמיטה לפני כניסתה, ודין תוספת שביעית";
        chaptersNames[SHMITA][3] = "פרק ג: פתיחה להלכות איסור המלאכה בקרקע ובצומח";
        chaptersNames[SHMITA][4] = "פרק ד: מהן המלאכות האסורות בשמיטה";
        chaptersNames[SHMITA][5] = "פרק ה: דין העציצים שבבית ובגינה";
        chaptersNames[SHMITA][6] = "פרק ו: הטיפול בגינה הפרטית בשמיטה";
        chaptersNames[SHMITA][7] = "פרק ז: מצוות הפקרת יבולי השביעית";
        chaptersNames[SHMITA][8] = "פרק ח: קדושת יבולי השביעית - פתיחה";
        chaptersNames[SHMITA][9] = "פרק ט: קדושת יבולי השביעית - באלו יבולים";
        chaptersNames[SHMITA][10] = "פרק י: קדושת השביעית - איסור הפסד והשחתת יבולי השביעית";
        chaptersNames[SHMITA][11] = "פרק יא: איסור מסחר ביבולי השביעית";
        chaptersNames[SHMITA][12] = "פרק יב: הקדושה שחלה על הכסף שקנו בו את יבולי השביעית";
        chaptersNames[SHMITA][13] = "פרק יג: איסור \"ספיחין\"";
        chaptersNames[SHMITA][14] = "פרק יד: אוצר בית דין";
        chaptersNames[SHMITA][15] = "פרק טו: דיני שביעית בקרקע שבבעלות גוי, וקניית סחורה מגויים תומכי טרור";
        chaptersNames[SHMITA][16] = "פרק טז: היתר המכירה";
        chaptersNames[SHMITA][17] = "פרק יז: מהו סדר העדיפויות ברכישת פירות וירקות של שנת השמיטה";
        chaptersNames[SHMITA][18] = "פרק יח: הנחיות מעשיות לצרכן בקניית יבולי השמיטה";
        chaptersNames[SHMITA][19] = "פרק יט: המצוות התלויות בארץ בשמיטה";
        chaptersNames[SHMITA][20] = "פרק כ: ביעור יבולי השביעית מהבית מהזמן שנגמרו בשדה";
        chaptersNames[SHMITA][21] = "פרק כא: ביעור המעשרות והוידוי על כך";
        chaptersNames[SHMITA][22] = "פרק כב: מדיני השמיטה השייכים לשנה השמינית";
        chaptersNames[SHMITA][23] = "פרק כג: שמיטת כספים";
        chaptersNames[SHMITA][24] = "פרק כד: התפילות בשמיטה, ומצוות השמיטה לעתיד לבוא";
        chaptersNames[SHMITA][25] = "פרק כה: מדיני השמיטה בבית המקדש";
        chaptersNames[SHMITA][26] = "פרק כו: מצוות הקהל";
        chaptersNames[SHMITA][27] = "פרק כז: שנת היובל";
        chaptersNames[SHMITA][28] = "*c*"+"חלק ב: נספחים";
        chaptersNames[SHMITA][29] = "נספח א: ליקוט ממשנת מורנו ורבנו הגאון הרב צבי יהודה";
        chaptersNames[SHMITA][30] = "נספח ב: דבר הרבנים הראשיים לישראל בענין היתר המכירה";
        chaptersNames[SHMITA][31] = "נספח ג:  מכתב אל הגר\"א שפירא זצ\"ל";
        chaptersNames[SHMITA][32] = "נספח ד: מכתב אל הגר\"א שפירא זצ\"ל";
        chaptersNames[SHMITA][33] = "נספח ה: מכתב בשם ועדת השמיטה בענין היתר המכירה";
        chaptersNames[SHMITA][34] = "נספח ו: מכתב הגר\"א שפירא זצ\"ל בענין היתר המכירה";
        chaptersNames[SHMITA][35] = "נספח ז: מכתב הגר\"א שפירא זצ\"ל";
        chaptersNames[SHMITA][36] = "נספח ח: מכתב הגר\"א שפירא והגר\"מ אליהו זצ\"ל, בענין סדר העדיפויות - לחקלאי";
        chaptersNames[SHMITA][37] = "נספח ט: דברי הגר\"א שפירא זצ\"ל בענין סדר עדיפויות בשמיטה - לצרכן";
        chaptersNames[SHMITA][38] = "נספח י: מכתב הגר\"א שפירא זצ\"ל בענין אתרוגי אוצר בית דין";
        chaptersNames[SHMITA][39] = "נספח יא: מכתב הגר\"א שפירא זצ\"ל והגר\"מ אליהו זצ\"ל,";
        chaptersNames[SHMITA][40] = "נספח יב: מכתב הגר\"א שפירא זצ\"ל: על מעמד \"זכר להקהל\"";
        chaptersNames[SHMITA][41] = "נספח יג: מכתב הגר\"ע יוסף זצ\"ל";
        chaptersNames[SHMITA][42] = "נספח יד:  מכתב הגר\"א נבנצל שליט\"א";
        chaptersNames[SHMITA][43] = "נספח טו: מכתב הגר\"ד ליאור שליט\"א";
        chaptersNames[SHMITA][44] = "נספח טז: מכתב הגר\"א וייס שליט\"א";
        chaptersNames[SHMITA][45] = "נספח יז: בענין שמירת קדושת יבולי שביעית בצה\"ל";
        chaptersNames[SHMITA][46] = "נספח יח: עוד בענין שמירת קדושת יבולי שביעית בצה\"ל";
        chaptersNames[SHMITA][47] = "נספח יט: מכתב הרב הראשי לצה\"ל הרה\"ג איל משה קרים שליט\"א";
        chaptersNames[SHMITA][48] = "נספח כ: דברי הראשל\"צ הגר\"י יוסף בענין היתר המכירה";
        chaptersNames[SHMITA][49] = "נספח כא: מכתב הגר\"ש עמאר שליט\"א - בענין קידושי אשה בירקות מהיתר מכירה";
        chaptersNames[SHMITA][50] = "נספח כב: האם \"יבול נוכרי\" הינו באמת \"יבול נוכרי\" (שגדל אצל גוי)";
        chaptersNames[SHMITA][51] = "נספח כג: האם \"יבול נוכרי\" הינו באמת \"יבול נוכרי\" (שגדל אצל גוי)";
        chaptersNames[SHMITA][52] = "נספח כד:  נוסח שטר הפרוזבול";
        chaptersNames[SHMITA][53] = "נספח כה: השיטות השונות לגבי הגבול הדרומי של הארץ לענייני השמיטה";

        /*PESACH*/
        chaptersNames[PESACH][0] = "הקדמה";
        chaptersNames[PESACH][1] = "פרק א: דיני ערב פסח (יום י\"ד בניסן)";
        chaptersNames[PESACH][2] = "פרק ב: עוד מדיני ערב פסח (יום י\"ד בניסן)";
        chaptersNames[PESACH][3] = "פרק ג: הלכות תפילת ערבית בליל פסח";
        chaptersNames[PESACH][4] = "פרק ד: הלכות הסדר: קדש";
        chaptersNames[PESACH][5] = "פרק ה: הלכות הסדר: רחץ, כרפס, יחץ";
        chaptersNames[PESACH][6] = "פרק ו: הלכות הסדר: מגיד";
        chaptersNames[PESACH][7] = "פרק ז: הלכות הסדר: רחצה, מוציא ומצה";
        chaptersNames[PESACH][8] = "פרק ח: הלכות הסדר: מרור, כורך";
        chaptersNames[PESACH][9] = "פרק ט: דיני הסדר: שלחן עורך, צפון וברך";
        chaptersNames[PESACH][10] = "פרק י: הלכות הסדר: הלל, נרצה, ושאר דיני לילה זה";
        chaptersNames[PESACH][11] = "*c*" + "- חלק ב: נספחים -";
        chaptersNames[PESACH][12] = "נספח א: מכתב הגר\"א שפירא זצ\"ל";
        chaptersNames[PESACH][13] = "נספח ב: מכתב הגר\"ח קנייבסקי שליט\"א";
        chaptersNames[PESACH][14] = "נספח ג: מכתב הג\"ר חיים קנייבסקי שליט\"א";
        chaptersNames[PESACH][15] = "נספח ד:  מכתב הג\"ר חיים קנייבסקי שליט\"א";
        chaptersNames[PESACH][16] = "נספח ה: מכתב הגר\"ש משאש זצ\"ל";
        chaptersNames[PESACH][17] = "נספח ו: מכתב הגר\"ש משאש זצ\"ל";
        chaptersNames[PESACH][18] = "נספח ז: מכתב הגר\"ש גורן זצ\"ל ועוד רבנים";
        chaptersNames[PESACH][19] = "נספח ח: מכתב הגר\"ש מן ההר זצ\"ל";
        chaptersNames[PESACH][20] = "נספח ט: מכתב הגר\"ד ליאור שליט\"א";
        chaptersNames[PESACH][21] = "נספח י: הערות הגר\"ש דבליצקי שליט\"א לספרנו (ניתן עם ההסכמה)";
        chaptersNames[PESACH][22] = "נספח יא: מכתב הגר\"ש דבליצקי שליט\"א";
        chaptersNames[PESACH][23] = "נספח יב: פסקי הלכות מהגר\"מ פינשטיין";
        chaptersNames[PESACH][24] = "נספח יג: מכתב הגאון רבי שריה דבליצקי שליט\"א";
        chaptersNames[PESACH][25] = "נספח יד: שני פירושים מרבינו החסיד הגר\"א זצוק\"ל על \"חד גדיא\"";
        chaptersNames[PESACH][26] = "נספח טו: קונטרס שיעור כזית";
        chaptersNames[PESACH][27] = "נספח טז: תשובת הגר\"ד ליאור שליט\"א";
        chaptersNames[PESACH][28] = "נספח יז: דין חולי צליאק באכילת מצות בליל הסדר";
        chaptersNames[PESACH][29] = "נספח יח: שאלה בענין גלאי חמץ";
        chaptersNames[PESACH][30] = "נספח יט: תשובת הג\"ר יעקב אריאל שליט\"א";
        chaptersNames[PESACH][31] = "נספח כ: תשובת הג\"ר זלמן נחמיה גולדברג שליט\"א";
        chaptersNames[PESACH][32] = "נספח כא: תשובת הג\"ר שלמה דיכובסקי שליט\"א";
        chaptersNames[PESACH][33] = "נספח כב: מכתב הג\"ר יצחק יוסף שליט\"א";
        chaptersNames[PESACH][34] = "נספח כג: תשובת הג\"ר דב ליאור שליט\"א";

        /*SUCA*/
        chaptersNames[SUCA][0] = "תוכן מפורט לקונטרס";
        chaptersNames[SUCA][1] = "הקדמה";
        chaptersNames[SUCA][2] = "פרק א: הסוכה – פתיחה ודינים כלליים";
        chaptersNames[SUCA][3] = "פרק ב: מידות הסוכה";
        chaptersNames[SUCA][4] = "פרק ג: דין מבנה הסוכה ודפנותיה";
        chaptersNames[SUCA][5] = "פרק ד: מדיני הסכך – ממה יהא עשוי הסכך";
        chaptersNames[SUCA][6] = "פרק ה: עוד מדיני הסכך";
        chaptersNames[SUCA][7] = "פרק ו: שאר דיני הסכך";
        chaptersNames[SUCA][8] = "פרק ז: דין הבעלות על הסוכה ומיקומה";
        chaptersNames[SUCA][9] = "פרק ח: דין קדושת הסוכה ואיסור ההנאה ממנה";
        chaptersNames[SUCA][10] = "פרק ט: גדרי חיוב הישיבה בסוכה";
        chaptersNames[SUCA][11] = "פרק י: דין ישיבת הסוכה בלילה הראשון: הקידוש, הסעודה והשינה";
        chaptersNames[SUCA][12] = "פרק יא: דין הפטורים מסוכה";
        chaptersNames[SUCA][13] = "פרק יב: מדיני הסוכה בשבת, ביום טוב, וביום השביעי והשמיני של החג";
        chaptersNames[SUCA][14] = "פרק יג: עוד מדיני חג הסוכות";
        chaptersNames[SUCA][15] = "*c*"+ "- חלק ב: נספחים -";
        chaptersNames[SUCA][16] = "נספח א: מכתב בענין סוכות ירושלים וסוכות נחלים";
        chaptersNames[SUCA][17] = "נספח א: מכתב הגרז\"נ גולדברג שליט\"א";
        chaptersNames[SUCA][18] = "נספח ב: מכתב הגרז\"נ גולדברג שליט\"א";
        chaptersNames[SUCA][19] = "נספח ג: מכתב הגרא\"ז וייס שליט\"א";
        chaptersNames[SUCA][20] = "נספח ד: מכתב הגר\"ד ליאור שליט\"א";
        chaptersNames[SUCA][21] = "נספח ה: מכתב הגר\"א נבנצל שליט\"א";
        chaptersNames[SUCA][22] = "נספח ו: מכתב נוסף מהגרז\"נ גולדברג שליט\"א";
        chaptersNames[SUCA][23] = "נספח ז: מכתב מהגר\"מ אליהו שליט\"א";
        chaptersNames[SUCA][24] = "נספח ח: מכתב מהגר\"ח קנייבסקי שליט\"א";
        chaptersNames[SUCA][25] = "נספח ט: מכתב הגר\"מ מזוז שליט\"א";
        chaptersNames[SUCA][26] = "נספח י: מכתב מהגר\"א נבנצל שליט\"א";
        chaptersNames[SUCA][27] = "נספח יא: מכתב הג\"ר דב ליאור שליט\"א";
        chaptersNames[SUCA][28] = "נספח יב : מכתב לג\"ר חיים קנייבסקי שליט\"א";
        chaptersNames[SUCA][29] = "נספח יג: מכתב בעניני גרמא בשבת ויום טוב";
        chaptersNames[SUCA][30] = "נספח יג:  מכתב הגרא\"ז וייס שליט\"א";
        chaptersNames[SUCA][31] = "נספח יד : מכתב הגר\"ד ליאור שליט\"א";
        chaptersNames[SUCA][32] = "נספח טו: מכתב הגר\"א נבנצל שליט\"א";
        chaptersNames[SUCA][33] = "נספח טז: קונטרס צירוף כמה הלכתות (=הלכות למשה מסיני) במציאות אחת.";
        chaptersNames[SUCA][34] = "נספח יז: קונטרס מידות האורך (בפרט לסוכות), לשיטת הגרא\"ח נאה";
        chaptersNames[SUCA][35] = "נספח יח: מכתב בעניני חזקות וסוכה גזולה";
        chaptersNames[SUCA][36] = "נספח יח: תשובת הגרז\"נ גולדברג שליט\"א";
        chaptersNames[SUCA][37] = "נספח יט: תשובת הגרא\"ז וייס שליט\"א";
        chaptersNames[SUCA][38] = "נספח כ: מכתב לרועי רוט הי\"ד";
        chaptersNames[SUCA][39] = "נספח כא: סיפורים על מו\"ר הגה\"צ הרב מרדכי אליהו זצ\"ל";

        /*ARBA_MINIM*/
        chaptersNames[ARBA_MINIM][0] = "הקדמה";
        chaptersNames[ARBA_MINIM][1] = "הלכות כשרות ארבעת המינים בקצרה";
        chaptersNames[ARBA_MINIM][2] = "פרק א: פתיחה לדיני ארבעת המינים";
        chaptersNames[ARBA_MINIM][3] = "פרק ב: דיני הלולב";
        chaptersNames[ARBA_MINIM][4] = "פרק ג: דיני ההדס";
        chaptersNames[ARBA_MINIM][5] = "פרק ד: דיני הערבה";
        chaptersNames[ARBA_MINIM][6] = "פרק ה: דיני האתרוג";
        chaptersNames[ARBA_MINIM][7] = "פרק ו: הלכות כלליות לגבי כשרות ארבעת המינים";
        chaptersNames[ARBA_MINIM][8] = "פרק ז: דיני נטילת ארבעת המינים";
        chaptersNames[ARBA_MINIM][9] = "פרק ח: זמן ומקום נטילת ארבעת המינים";
        chaptersNames[ARBA_MINIM][10] = "פרק ט: דין אגידת שלושת המינים";
        chaptersNames[ARBA_MINIM][11] = "פרק י: הבעלות על ארבעת המינים והקנאתם לאחרים";
        chaptersNames[ARBA_MINIM][12] = "פרק יא: אופן נטילת ארבעת המינים והברכות עליהם";
        chaptersNames[ARBA_MINIM][13] = "פרק יב: אופן הנענועים של ארבעת המינים";
        chaptersNames[ARBA_MINIM][14] = "פרק יג: דיני ארבעת המינים בשבת וביום טוב";
        chaptersNames[ARBA_MINIM][15] = "פרק יד: קדושת ארבעת המינים: הקצאתם למצוותם, שימוש בהם ואיסור ביזויים";
        chaptersNames[ARBA_MINIM][16] = "*c*" +"- חלק ב: שער התפילות -";
        chaptersNames[ARBA_MINIM][17] = "פרק א: הלכות תפילת ערבית של ליל החג";
        chaptersNames[ARBA_MINIM][18] = "פרק ב: הזמנת האושפיזין לסוכה";
        chaptersNames[ARBA_MINIM][19] = "פרק ג: דיני תפילת שחרית של יום טוב ראשון של סוכות";
        chaptersNames[ARBA_MINIM][20] = "*c*" +"- חלק ג: נספחים -";
        chaptersNames[ARBA_MINIM][21] = "נספח א: שאלה בעניני קניינים של ארבעת המינים";
        chaptersNames[ARBA_MINIM][22] = "נספח ב: תשובת הגר\"י אריאל שליט\"א";
        chaptersNames[ARBA_MINIM][23] = "נספח ג: תשובת הגרז\"נ גולדברג שליט\"א";
        chaptersNames[ARBA_MINIM][24] = "נספח ד: תשובת הגרא\"ז וייס שליט\"א";
        chaptersNames[ARBA_MINIM][25] = "נספח ה: תשובת הגר\"ד ליאור שליט\"א";
        chaptersNames[ARBA_MINIM][26] = "נספח ו: תשובת הגר\"א נבנצל שליט\"א";
        chaptersNames[ARBA_MINIM][27] = "נספח ז: שאלה בענין חציצה בלולבים המשוחים בחומר";
        chaptersNames[ARBA_MINIM][28] = "נספח ח: תשובת הגר\"י אריאל שליט\"א";
        chaptersNames[ARBA_MINIM][29] = "נספח ט: תשובת הגרז\"נ גולדברג שליט\"א";
        chaptersNames[ARBA_MINIM][30] = "נספח י: תשובת הגרא\"ז וייס שליט\"א";
        chaptersNames[ARBA_MINIM][31] = "נספח יא: תשובת הגר\"ד ליאור שליט\"א";
        chaptersNames[ARBA_MINIM][32] = "נספח יב: תשובת הגר\"א נבנצל שליט\"א";
        chaptersNames[ARBA_MINIM][33] = "נספח יג: הצורך שהלולב יהא גבוה טפח מההדסים והערבות";
        chaptersNames[ARBA_MINIM][34] = "נספח יד: באלו קשרים מותר לאגוד את שלושת המינים ביום טוב";

        /*SHABAT_B*/
        chaptersNames[SHABAT_B][0] = "הקדמה";
        chaptersNames[SHABAT_B][1] = "שם המכשיר והלכותיו";
        chaptersNames[SHABAT_B][2] = "*c*"+"- חלק ב: מילואים -";
        chaptersNames[SHABAT_B][3] = "פרק א: אינטרנט - השארת האתר ותא הדואר האלקטרוני פתוחים בשבת";
        chaptersNames[SHABAT_B][4] = "פרק ב: דין מכשירים מגבירי קול בכלל, ואינטרקום בפרט";
        chaptersNames[SHABAT_B][5] = "פרק ג: דין הפעלת אינטרקום מחדר לחדר בשבת כדי לשמוע אם תינוק בוכה";
        chaptersNames[SHABAT_B][6] = "פרק ד: אינטרקום בכניסה לבית - דינים פרטיים";
        chaptersNames[SHABAT_B][7] = "פרק ה: דין מי שלחץ בשבת בטעות על כפתור פעמון (האינטרקום וכדומה), המותר להרפותו";
        chaptersNames[SHABAT_B][8] = "פרק ו: שימוש בשבת במכשיר אינפוזיה חשמלי";
        chaptersNames[SHABAT_B][9] = "פרק ז: שימוש בשבת במכשיר איתורית (ביפר)";
        chaptersNames[SHABAT_B][10] = "פרק ח: שימוש במחשבים בשבת (לפיקוח נפש), בחול המועד, ועוד";
        chaptersNames[SHABAT_B][11] = "פרק ט: כשסוללת האיתורית עומדת להגמר בשבת, העדיף לכבות המכשירכשאינו בכוננות, או להמתין שתגמר הסוללה ואז להחליפה בחדשה";
        chaptersNames[SHABAT_B][12] = "*c*"+" -חלק ג: נספחים -";
        chaptersNames[SHABAT_B][13] = "נספח א:  מכתב הגרל\"י הלפרין שליט\"א";
        chaptersNames[SHABAT_B][14] = "נספח ב: מכתב הגר\"ד ליאור שליט\"א";
        chaptersNames[SHABAT_B][15] = "נספח ג: מכתב הגר\"ח קנייבסקי שליט\"א";
        chaptersNames[SHABAT_B][16] = "נספח ד: מכתב הגר\"א נבנצל שליט\"א";
        chaptersNames[SHABAT_B][17] = "נספח ה: מכתב הגר\"א נבנצל שליט\"א";
        chaptersNames[SHABAT_B][18] = "נספח ו: מכתב הגר\"א נבנצל שליט\"א";
        chaptersNames[SHABAT_B][19] = "נספח ז: מכתב הגר\"א נבנצל שליט\"א";
        chaptersNames[SHABAT_B][20] = "נספח ח: מכתב הגר\"ש גורן זצ\"ל";
        chaptersNames[SHABAT_B][21] = "נספח ט: ציורים";

        /*kitzurim*/
        chaptersNames[KITZURIM][0] = "הקדמה";
        chaptersNames[KITZURIM][1] = "הלכות ראש השנה";
        chaptersNames[KITZURIM][2] = "הלכות יום הכיפורים";
        chaptersNames[KITZURIM][3] = "הלכות סוכה";
        chaptersNames[KITZURIM][4] = "הלכות ארבעת המינים";
        chaptersNames[KITZURIM][5] = "הלכות טו בשבט";
        chaptersNames[KITZURIM][6] = "הלכות חנוכה";
        chaptersNames[KITZURIM][7] = "הלכות פורים";
        chaptersNames[KITZURIM][8] = "הלכות פסח";
        chaptersNames[KITZURIM][9] = "הלכות יום טוב";
        chaptersNames[KITZURIM][10] = "הלכות חול המועד";
        chaptersNames[KITZURIM][11] = "הלכות ספירת העומר";
        chaptersNames[KITZURIM][12] = "הלכות חג השבועות";
        chaptersNames[KITZURIM][13] = "הלכות שלוש התעניות בין המצרים ותשעה באב";



    }

    public String getHascamaName(int id){
        if ((id >= 0) && (id <= HASCAMOT_NUMBER))
            return "hascama" + id;
        else
            return "hascama0";
    }
    public void loadData(){
        /*fill books list*/
        for (int i = 0; i < BOOKS_NUMBER; i++)
            booksHeaders.add(convertBookIdToName(i));

        /*fill parts list*/
        /*hanuca*/
        partsHeaders.add(new ArrayList<String>());
        partsHeaders.get(HANUCA).add("הקדמה");
        partsHeaders.get(HANUCA).add("גוף הספר");
        partsHeaders.get(HANUCA).add("נספחים");

        /*ben hametzraeim*/
        partsHeaders.add(new ArrayList<String>());
        partsHeaders.get(BEN_HAMETZARIM).add("הקדמה");
        partsHeaders.get(BEN_HAMETZARIM).add("חלק א - הלכות שלש התעניות");
        partsHeaders.get(BEN_HAMETZARIM).add("חלק ב - הלכות בין המצרים");
        partsHeaders.get(BEN_HAMETZARIM).add("חלק ג - הלכות תשעה באב");
        partsHeaders.get(BEN_HAMETZARIM).add("נספחים");


        /*fill chapters list*/
        /*hanuca*/
        mchaptersNames.add(new ArrayList<>());
        for (int i = 0; i<partsHeaders.get(HANUCA).size(); i++)
            mchaptersNames.get(HANUCA).add(new ArrayList<>());
        mchaptersNames.get(HANUCA).get(0).add("הקדמה: הקדמה");
        mchaptersNames.get(HANUCA).get(1).add("פרק א: הלכות הדברים האסורים והמותרים בחנוכה");
        mchaptersNames.get(HANUCA).get(1).add("פרק ב: סידור נרות החנוכה, מספרם וכמות השמן");
        mchaptersNames.get(HANUCA).get(1).add("פרק ג: מקום הנחת נרות החנוכה");
        mchaptersNames.get(HANUCA).get(1).add("פרק ד: זמן הדלקת נרות החנוכה");
        mchaptersNames.get(HANUCA).get(1).add("פרק ה: דין השמנים, הפתילות והחנוכיות");
        mchaptersNames.get(HANUCA).get(1).add("פרק ו: איסור שימוש בנרות (הנאה מאורם והדלקה מנר לנר)");
        mchaptersNames.get(HANUCA).get(1).add("פרק ז: דין הדלקה עושה מצוה, וכבתה אין זקוק לה");
        mchaptersNames.get(HANUCA).get(1).add("פרק ח: סדר הברכות וההדלקה");
        mchaptersNames.get(HANUCA).get(1).add("פרק ט: מי חייב בנר חנוכה, ודין אורח (כולל דין בחורי ישיבה, בנות בפנימיה וכדו')");
        mchaptersNames.get(HANUCA).get(1).add("פרק י: הדלקת נרות החנוכה בבית הכנסת");
        mchaptersNames.get(HANUCA).get(1).add("פרק יא: הלכות הדלקת נרות החנוכה");
        mchaptersNames.get(HANUCA).get(1).add("פרק יב: סדר התפילה בחנוכה");
        mchaptersNames.get(HANUCA).get(1).add("פרק יג: קריאת התורה בחנוכה");
        mchaptersNames.get(HANUCA).get(2).add("נספח א: תשובות לשאלות תלמידי ישיבת \"מרכז הרב\"");
        mchaptersNames.get(HANUCA).get(2).add("נספח ב: מכתב הגר\"א שפירא זצ\"ל");
        mchaptersNames.get(HANUCA).get(2).add("נספח ג: מכתב לג\"ר שלום משאש זצ\"ל, רבה הראשי של ירושלים.");
        mchaptersNames.get(HANUCA).get(2).add("נספח ד: מכתב לג\"ר חיים קנייבסקי שליט\"א");
        mchaptersNames.get(HANUCA).get(2).add("נספח ה: מכתב לג\"ר חיים קנייבסקי שליט\"א");
        mchaptersNames.get(HANUCA).get(2).add("נספח ו: מכתב לגר\"ש דבליצקי שליט\"א");
        mchaptersNames.get(HANUCA).get(2).add("נספח ז: קובץ מהלכות חנוכה מהג\"ר משה פיינשטיין זצ\"ל");
        mchaptersNames.get(HANUCA).get(2).add("נספח ח: מכתב הגר\"ש משאש זצ\"ל");
        mchaptersNames.get(HANUCA).get(2).add("נספח ט: מכתב הג\"ר מרדכי אליהו זצ\"ל");
        mchaptersNames.get(HANUCA).get(2).add("נספח י: מכתב הג\"ר שריה דבליצקי שליט\"א");
        mchaptersNames.get(HANUCA).get(2).add("נספח יא: מכתב הג\"ר אביגדר הלוי נבנצל שליט\"א");
        mchaptersNames.get(HANUCA).get(2).add("נספח יב: הגר\"ע יוסף זצ\"ל – קווים לדמותו");
        mchaptersNames.get(HANUCA).get(2).add("נספח יג: מגילת אנטיוכוס");
        /*ben hametzraeim*/
        mchaptersNames.add(new ArrayList<>());
        for (int i = 0; i< partsHeaders.get(BEN_HAMETZARIM).size(); i++)
            mchaptersNames.get(BEN_HAMETZARIM).add(new ArrayList<>());
        mchaptersNames.get(BEN_HAMETZARIM).get(0).add("הקדמה:  הקדמה");
        mchaptersNames.get(BEN_HAMETZARIM).get(1).add("פרק א: איסור האכילה בשלוש התעניות והפטורים מהתענית");
        mchaptersNames.get(BEN_HAMETZARIM).get(1).add("פרק ב: דיני התפילות בשלש התעניות");
        mchaptersNames.get(BEN_HAMETZARIM).get(1).add("פרק ג: שאר דיני שלש התעניות");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק מקדים: הלכות בין המצרים לפי סדר הזמנים");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק א: איסור אכילת בשר ושתיית יין");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק ב: בגד חדש - לבישתו");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק ג: בגד נקי - לבישתו");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק ד: בניה ונטיעה");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק ה: טיולים ובילויים");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק ו: כיבוס וגיהוץ");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק ז: נגינה בכלי נגינה (שמיעה וניגון)");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק ח: סכנה - זהירות ממנה");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק ט: סירוק שיער");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק י: צחצוח נעלים");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק יא: צפורנים - גזיזתן");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק יב: קידושין (אירוסים) ונישואים");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק יג: קנית דבר חדש ומסחר");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק יד: רחיצה");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק טו: רצפה - שטיפתה");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק טז: רקודים ומחולות");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק יז: שבוע שחל בו תשעה באב, זמן חלוּתוֹ");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק יח: \"שהחיינו\", ברכתה בימים אלה");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק יט: שירה");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק כ: תביעה בבית דין");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק כא: תספורת וגילוח");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק כב: תפילות והנהגות");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק כג: תפירת ותיקון בגדים");
        mchaptersNames.get(BEN_HAMETZARIM).get(2).add("פרק כד: תשמיש המיטה");
        mchaptersNames.get(BEN_HAMETZARIM).get(3).add("פרק א: צום תשעה באב, מקורו וטעמו");
        mchaptersNames.get(BEN_HAMETZARIM).get(3).add("פרק ב: דין ערב תשעה באב (יום ח' באב. כולל דיני הסעודה המפסקת)");
        mchaptersNames.get(BEN_HAMETZARIM).get(3).add("פרק ג: דין ליל תשעה באב (כולל תפילת ערבית)");
        mchaptersNames.get(BEN_HAMETZARIM).get(3).add("פרק ד: דין איסור אכילה ושתיה בתשעה באב");
        mchaptersNames.get(BEN_HAMETZARIM).get(3).add("פרק ה: דין הפטורים מן התענית בתשעה באב");
        mchaptersNames.get(BEN_HAMETZARIM).get(3).add("פרק ו: שאר איסורי היום (כולל רחיצה, סיכה, נעילת נעלים ותשמיש המיטה).");
        mchaptersNames.get(BEN_HAMETZARIM).get(3).add("פרק ז: עוד מהדברים האסורים בתשעה באב");
        mchaptersNames.get(BEN_HAMETZARIM).get(3).add("פרק ח: דיני תפילות היום (שחרית ומנחה) בתשעה באב");
        mchaptersNames.get(BEN_HAMETZARIM).get(3).add("פרק ט: עוד מדיני תשעה באב");
        mchaptersNames.get(BEN_HAMETZARIM).get(3).add("פרק י: דין תשעה באב שחל בשבת או ביום ראשון");
        mchaptersNames.get(BEN_HAMETZARIM).get(3).add("פרק יא: הלכות מוצאי תשעה באב");
        mchaptersNames.get(BEN_HAMETZARIM).get(4).add("נספח א: מכתב הג\"ר שלמה גורן זצ\"ל");
        mchaptersNames.get(BEN_HAMETZARIM).get(4).add("נספח ב: שמיעת מוזיקה ווקאלית בימי בין המצרים");
        mchaptersNames.get(BEN_HAMETZARIM).get(4).add("נספח ג: סיפורים מהשואה");
        mchaptersNames.get(BEN_HAMETZARIM).get(4).add("נספח ד: קינות על השואה");
        mchaptersNames.get(BEN_HAMETZARIM).get(4).add("נספח ה: קינה על חורבן גוש קטיף וצפון השומרון");
        mchaptersNames.get(BEN_HAMETZARIM).get(4).add("נספח ו: מדרש אלה אזכרה (עשרת הרוגי מלכות)");
        mchaptersNames.get(BEN_HAMETZARIM).get(4).add("נספח ז: מדרש נחמה - \"מנחם משיב נפשי\"");


    }
}
