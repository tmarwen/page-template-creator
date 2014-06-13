package org.exoplatform.support.cgvaldoise.page;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by eXo Platform MEA on 13/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public class NavigationBuilder
{
  private final static String LINEBREAK = "\n";
  private final static String INDENTATION = "    ";
  private final static String PAGE_URI = "<uri>${pageName}${pageId}</uri>";
  private final static String PAGE_NAME = "<name>${pageName}${pageId}</name>";
  private final static String SIMPLE_PAGE_LABEL = "<label>#{portal.acme.${pageName}${pageId}}</label>";
  private final static String TWO_LEVEL_PAGE_LABEL = "<label>#{portal.acme.${pageName}${parentPageId}.${pageName}${childPageId}}</label>";
  private final static String SIMPLE_PAGE_REFERENCE = "<page-reference>portal::acme::${pageName}${pageId}</page-reference>";
  private final static String TWO_LEVEL_PAGE_REFERENCE = "<page-reference>portal::acme::${pageName}${parentPageId}::${pageName}${childPageId}</page-reference>";
  private final static String PAGE_ID = "${pageId}";
  private final static String PARENT_ID = "${parentPageId}";
  private final static String CHILD_ID = "${childPageId}";
  private static final String PAGE_NAME_KEY = "${pageName}";

  public static void buildOneLevelNavigation(StringBuilder navigationBuilder, String pageName, String pageId)
  {
    navigationBuilder
        .append("<node>")
        .append(LINEBREAK)
        .append(INDENTATION).append(
          StringUtils.replace(
              StringUtils.replace(PAGE_URI, PAGE_ID, pageId),
              PAGE_NAME_KEY,
              pageName))
        .append(LINEBREAK)
        .append(INDENTATION).append(
          StringUtils.replace(
              StringUtils.replace(PAGE_NAME, PAGE_ID, pageId),
              PAGE_NAME_KEY,
              pageName))
        .append(LINEBREAK)
        .append(INDENTATION).append(
          StringUtils.replace(
              StringUtils.replace(SIMPLE_PAGE_LABEL, PAGE_ID, pageId),
              PAGE_NAME_KEY,
              pageName))
        .append(LINEBREAK)
        .append(INDENTATION).append("<visibility>SYSTEM</visibility>")
        .append(LINEBREAK)
        .append(INDENTATION).append(
          StringUtils.replace(
              StringUtils.replace(SIMPLE_PAGE_REFERENCE, PAGE_ID, pageId),
              PAGE_NAME_KEY,
              pageName))
        .append(LINEBREAK)
        .append("</node>")
        .append(LINEBREAK);
  }

  public static void buildTwoLevelNavigation(StringBuilder navigationBuilder, String pageName, String pageId, String parentId)
  {
    navigationBuilder
        .append("<node>")
        .append(LINEBREAK)
        .append(INDENTATION).append(
        StringUtils.replace(
            StringUtils.replace(PAGE_URI, PAGE_ID, pageId),
            PAGE_NAME_KEY,
            pageName))
        .append(LINEBREAK)
        .append(INDENTATION).append(
        StringUtils.replace(
            StringUtils.replace(PAGE_NAME, PAGE_ID, pageId),
            PAGE_NAME_KEY,
            pageName))
        .append(LINEBREAK)
        .append(INDENTATION).append(
        StringUtils.replace(
            StringUtils.replace(
                StringUtils.replace(TWO_LEVEL_PAGE_LABEL, PARENT_ID, parentId),
                CHILD_ID,
                pageId),
            PAGE_NAME_KEY,
            pageName))
        .append(LINEBREAK)
        .append(INDENTATION).append("<visibility>SYSTEM</visibility>")
        .append(LINEBREAK)
        .append(INDENTATION).append(
        StringUtils.replace(
            StringUtils.replace(
                StringUtils.replace(TWO_LEVEL_PAGE_REFERENCE, PARENT_ID, parentId),
                CHILD_ID,
                pageId),
            PAGE_NAME_KEY,
            pageName))
        .append(LINEBREAK)
        .append("</node>")
        .append(LINEBREAK);
  }
}
