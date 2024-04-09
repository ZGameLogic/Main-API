package com.zgamelogic.data.database.seaOfThieves;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

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
    private boolean success;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime proposed;
}
