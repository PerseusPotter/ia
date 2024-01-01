package com.perseuspotter.ia.util;

import java.time.Instant;
import java.time.ZonedDateTime;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class ROTBuilder {

  private Project proj;

  public ROTBuilder(Project proj) {
    this.proj = proj;
  }

  public String build() throws Exception {
    if (!this.proj.hasRepo()) throw new Exception(
      "must have git repo to build rot"
    );

    Repository repo = this.proj.getRepo();
    RevWalk walker = new RevWalk(repo);
    StringBuilder str = new StringBuilder();

    RevCommit commit;
    //<table><tr><td><p><span>CRITERION NUMBER</span><td><p><span>BLANK</span><td><p><span>DESC</span><td><p><span>BLANK</span><td><p><span>MONTH DAY, YEAR</span><td><p><span>CRITERION</span></table>
    str.append("<table>");
    int n = 1;
    while ((commit = walker.next()) != null) {
      str.append("<tr>");

      str.append("<td><p><span>");
      str.append("C");
      str.append(n++);
      str.append("</span></p></td>");

      str.append("<td><p><span>");
      str.append("</span></p></td>");

      str.append("<td><p><span>");
      str.append(commit.getFullMessage());
      str.append("</span></p></td>");

      str.append("<td><p><span>");
      str.append("</span></p></td>");

      str.append("<td><p><span>");
      Instant t = commit.getAuthorIdent().getWhenAsInstant();
      ZonedDateTime d = t.atZone(commit.getAuthorIdent().getZoneId());
      str.append(d.getMonth().toString());
      str.append(" ");
      str.append(d.getDayOfMonth());
      str.append(", ");
      str.append(d.getYear());
      str.append("</span></p></td>");

      str.append("<td><p><span>");
      str.append("C");
      str.append("</span></p></td>");

      str.append("</tr>");
    }
    str.append("</table>");

    walker.close();

    return str.toString();
  }
}
