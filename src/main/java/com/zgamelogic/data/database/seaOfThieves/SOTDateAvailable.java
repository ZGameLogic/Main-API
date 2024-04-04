package com.zgamelogic.data.database.seaOfThieves;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "sot_data")
@Data
public class SOTDateAvailable {
    @Id
    @GeneratedValue
    private long id;
    private boolean ben;
    private boolean patrick;
    private boolean jj;
    private boolean greg;
    private LocalDateTime proposed;
}
