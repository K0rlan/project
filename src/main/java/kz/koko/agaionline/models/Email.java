package kz.koko.agaionline.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sendemail")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String guestname;
    private String guestemail;
    private long guestphone;
    private String guestmessagetitle;
    private String guestmessage;

    public Email(String guestname, String guestemail, long guestphone, String guestmessagetitle, String guestmessage) {
        this.guestname = guestname;
        this.guestemail = guestemail;
        this.guestphone = guestphone;
        this.guestmessagetitle = guestmessagetitle;
        this.guestmessage = guestmessage;
    }
}
