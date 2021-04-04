package com.hardworkgroup.bridge_health_system.common_model.domain.bridge_inspection.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hardworkgroup.bridge_health_system.common_model.domain.system.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * (ProblemEventPicture)实体类
 *
 * @author hui
 * @since 2022-03-26 18:08:32
 */
@Entity
@Table(name = "problem_event_picture")
@Data
@NoArgsConstructor
public class ProblemEventPicture {

    @Id
    private Integer problemPictureID;

    private Integer problemEventID;

    private String problemPicture;

    //@ManyToOne
    //private ProblemEvent problemEvent;
}