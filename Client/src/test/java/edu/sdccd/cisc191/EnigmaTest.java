package edu.sdccd.cisc191;

import edu.sdccd.cisc191.ciphers.Enigma;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnigmaTest {

    @Test
    public void testEnigmaEncryption() {
        Enigma enigma = new Enigma(new int[]{1,1,1}, new int[]{2,2,2}, new int[]{3,3,3}, 'B', "AB CD EF GH IJ");
        assertEquals("XKCCEGGHAX", enigma.encode("HEILHITLER"));
        assertEquals("HEILHITLER", enigma.encode("XKCCEGGHAX".toUpperCase().replaceAll("[^A-Z]", "")));
    }

    @Test
    public void testLongMessage() {
        String inputText = "I PROPOSE to consider the question, ‘Can machines think?’ This should begin with definitions of the meaning of the terms ‘machine’ and ‘think’. The definitions might be framed so as to reflect so far as possible the normal use of the words, but this attitude is dangerous. If the meaning of the words ‘machine’ and ‘think’ are to be found by examining how they are commonly used it is difficult to escape the conclusion that the meaning and the answer to the question, ‘Can machines think?’ is to be sought in a statistical survey such as a Gallup poll. But this is absurd. Instead of attempting such a definition I shall replace the question by another, which is closely related to it and is expressed in relatively unambiguous words.";
        String plainText = inputText.toUpperCase().replaceAll("[^A-Z]", "");

        Enigma enigma = new Enigma(new int[]{2,1,1}, new int[]{3,21,1}, new int[]{5,25,1}, 'B', "AB CD EF GH IJ");
        String cipherText = enigma.encode(plainText);

        assertEquals("BIWJFMWYYXRJMDWWWDEVDERRLXHIXZBLYVKIHLHJMSXBVVCWXREVROTNHXOXHWLRGHBXPLVGLWXTLUGFSLGWGUXZAXABNZHUZIBHNYXJYHSHXPDBKFZZQQOHFNLLHJVZLXGEKJOUVQRUUEQXVXABMIXSCEMYBSXMDOYLJMZOAXJXLUFEKGJVJUGVXPSPNXJPSOENBEJERHSYKHJPOIZFVDDHNAXKOFLSLQBJOWZTFTZJLLQSNELPMFODEZBLHAOPWHJZPGUYPTFIWNTIFMIMDFAFUVKQXJXXRNOYUGMIAXJXASRZJSOFZSFCDGGUNNQSTTGPLCIZMSRGDOHFSNKQSLVMRSTENKWRZESZPWDZOLUPQQMOVYIEOCLOIESCDCWKCOVDEZYJZNBDLLFBVJJNOWCHWWEHGWKNTJLJAJQIEDHFAAFIFGNQEWMVYMECGSRBDQHODLILGNGWZFPLGBZOKQRYJDIRBHGBREZOAHGCFVOCNKVPAXLZVVXYKKSSAUIGFFAREBCWMAXWTGNENTOMTRCRPWOACQOFCUAYIJAPLMFBJHLVMFAOYHYLQPJXYEADJYIABSAT",
                cipherText);
        assertEquals(plainText, enigma.encode(cipherText));
    }

    @Test
    public void testEnigmaCryptanalysis() {
        String inputText = "QKRQW UQTZK FXZOM JFOYR HYZWV BXYSI WMMVW BLEBD MWUWB TVHMR\n" +
                "FLKSD CCEXI YPAHR MPZIO VBBRV LNHZU POSYE IPWJT UGYOS LAOXR\n" +
                "HKVCH QOSVD TRBPD JEUKS BBXHT TGVHG FICAC VGUVO QFAQW BKXZJ\n" +
                "SQJFZ PEVJR OJTOE SLBQH QTRAA HXVYA UHTNB GIBVC LBLXC YBDMQ\n" +
                "RTVPY KFFZX NDDPC CJBHQ FDKXE EYWPB YQWDX DRDHN IGDXE UJJPV\n" +
                "MHUKP CFHLL FERAZ HZOHX DGBKO QXKTL DVDCW KAEDH CPHJI WZMMT\n" +
                "UAMQE NNFCH UIAWC CHNCF YPWUA RBBNI EPHGD DKMDQ LMSNM TWOHM\n" +
                "AUHRH GCUMQ PKQRK DVSWV MTYVN FFDDS KIISX ONXQH HLIYQ SDFHE\n" +
                "NCMCO MREZQ DRPBM RVPQT VRSWZ PGLPI TRVIB PXXHP RFISZ TPUEP\n" +
                "LKOTT XNAZM HTJPC HAASF ZLEFC EZUTP YBAOS KPZCJ CYZOV APZZV\n" +
                "ELBLL ZEVDC HRMIO YEPFV UGNDL ENISX YCHKS JUWVX USBIT DEQTC\n" +
                "NKRLS NXMXY ZGCUP AWFUL TZZSF AHMPX GLLNZ RXYJN SKYNQ AMZBU\n" +
                "GFZJC URWGT QZCTL LOIEK AOISK HAAQF OPFUZ IRTLW EVYWM DN";
        String plainText = inputText.toUpperCase().replaceAll("[^A-Z]", "");

        /*Enigma enigma = new Enigma(new int[]{2,1,2}, new int[]{1,23,7}, new int[]{3,4,9}, 'B', "EZ RW MV IU BL PX JO");
        String cipherText = enigma.encode(plainText);*/
        String cipherText = plainText;

        Enigma enigmaTest = new Enigma(new int[]{4,20,3}, new int[]{2,10,14}, new int[]{1,5,10}, 'B', "");
        String temp = enigmaTest.encode(cipherText);
        System.out.println(CipherTools.findIndexOfCoincidence(temp.length(), CipherTools.getLetterFrequency(temp)));

        Enigma.cryptanalyze(cipherText);
        }
    }