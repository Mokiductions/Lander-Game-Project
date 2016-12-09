/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db_models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Miquel Ginés Borràs
 * @mail ginesborrasm@gmail.com
 */
@Entity
@Table(name = "mgines_games")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Games.findAll", query = "SELECT g FROM Games g"),
    @NamedQuery(name = "Games.findById", query = "SELECT g FROM Games g WHERE g.id = :id"),
    @NamedQuery(name = "Games.findByGameTime", query = "SELECT g FROM Games g WHERE g.gameTime = :gameTime"),
    @NamedQuery(name = "Games.findTopScores", query = "SELECT g FROM Games g ORDER BY g.score DESC"),
    @NamedQuery(name = "Games.findMostPlayed", query = "SELECT g.usrId, count(g) FROM Games g GROUP BY g.usrId ORDER BY count(g) DESC"),
    @NamedQuery(name = "Games.findLastActiveUsers", query = "SELECT g.usrId, max(g.gameTime) FROM Games g INNER JOIN Games g2 ON g.usrId = g2.usrId WHERE g.gameTime >= :gameTime GROUP BY g.usrId"),
    @NamedQuery(name = "Games.findByScore", query = "SELECT g FROM Games g WHERE g.score = :score")})
    //"SELECT g FROM Games g WHERE g.gameTime >= :gameTime"
public class Games implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "game_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gameTime;
    @Column(name = "score")
    private Integer score;
    @JoinColumn(name = "usr_id", referencedColumnName = "id")
    @ManyToOne
    private Users usrId;

    public Games() {
    }

    public Games(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getGameTime() {
        return gameTime;
    }

    public void setGameTime(Date gameTime) {
        this.gameTime = gameTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Users getUsrId() {
        return usrId;
    }

    public void setUsrId(Users usrId) {
        this.usrId = usrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Games)) {
            return false;
        }
        Games other = (Games) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db_models.Games[ id=" + id + " ]";
    }

}
