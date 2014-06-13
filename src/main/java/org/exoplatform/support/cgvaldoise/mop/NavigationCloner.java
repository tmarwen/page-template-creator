package org.exoplatform.support.cgvaldoise.mop;

import org.apache.commons.lang3.StringUtils;
import org.exoplatform.support.cgvaldoise.page.NavigationBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * Created by eXo Platform MEA on 12/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 */
public class NavigationCloner
{
  private final static Logger LOG = Logger.getLogger(NavigationCloner.class.getName());
  private final static int TOTAL_PARENT_PAGES = 25;
  private final static int TOTAL_CHILD_PAGES = 25;
  private final static int TOTAL_PAGES = TOTAL_PARENT_PAGES + (TOTAL_PARENT_PAGES * TOTAL_CHILD_PAGES);

  public static void main(String[] args)
  {
    int firstChildIndex = TOTAL_PARENT_PAGES + 1;
    int lastChildIndex = TOTAL_PAGES;

    File pages = new File("target/navigation.xml");
    if (pages.exists()) {
      pages.delete();
    }
    FileOutputStream fileOutputStream;
    PrintWriter writer = null;

    try
    {
      fileOutputStream = new FileOutputStream(pages);
      writer = new PrintWriter(fileOutputStream);
      int pageIndex = firstChildIndex;

      //Create Parent pages catalog1 ... catalog$TOTAL_PARENT_PAGES
      for (int i = 1; i <= NavigationCloner.TOTAL_PARENT_PAGES; i++)
      {
        StringBuilder builder = new StringBuilder();
        NavigationBuilder.buildOneLevelNavigation(builder, "simplepage", String.valueOf(i));
        writer.println(builder.toString());
        writer.flush();
        LOG.info("Page " + i + " navigation has been appended to pages file.");
      }

      //Create Child pages: every parent page already created, will have 100 child page
      for (int i = 1; i <= NavigationCloner.TOTAL_PARENT_PAGES; i++)
      {
        while ((pageIndex <= lastChildIndex))
        {
          StringBuilder builder = new StringBuilder();
          /*builder
              .append("<node>")
              .append(NavigationCloner.LINEBREAK)
              .append(NavigationCloner.INDENTATION).append(StringUtils.replace(NavigationCloner.PAGE_URI, NavigationCloner.CATALOG_ID, String.valueOf(pageIndex)))
              .append(NavigationCloner.LINEBREAK)
              .append(NavigationCloner.INDENTATION).append(StringUtils.replace(NavigationCloner.PAGE_NAME, NavigationCloner.CATALOG_ID, String.valueOf(pageIndex)))
              .append(NavigationCloner.LINEBREAK)
              .append(NavigationCloner.INDENTATION).append(
                    StringUtils.replace(
                        StringUtils.replace(
                            NavigationCloner.PAGE_LABEL,
                            NavigationCloner.PARENT_ID,
                            String.valueOf(i)),
                        NavigationCloner.CHILD_ID,
                        String.valueOf(pageIndex)))
              .append(NavigationCloner.LINEBREAK)
              .append(NavigationCloner.INDENTATION).append("<visibility>SYSTEM</visibility>")
              .append(NavigationCloner.LINEBREAK)
              .append(NavigationCloner.INDENTATION).append(
                    StringUtils.replace(
                         StringUtils.replace(
                             NavigationCloner.PAGE_REFERENCE,
                             NavigationCloner.PARENT_ID,
                             String.valueOf(i)),
                        NavigationCloner.CHILD_ID,
                        String.valueOf(pageIndex)))
              .append(NavigationCloner.LINEBREAK)
              .append("</node>")
              .append(NavigationCloner.LINEBREAK);*/
          NavigationBuilder.buildTwoLevelNavigation(builder, "simplepage", String.valueOf(pageIndex), String.valueOf(i));

          writer.println(builder.toString());
          writer.flush();
          LOG.info("Page " + pageIndex + " navigation has been appended to pages file.");
          pageIndex++;
          if (((pageIndex - TOTAL_PARENT_PAGES) % TOTAL_CHILD_PAGES) == 1)
          {
            break;
          }
        }
      }
    } catch (FileNotFoundException e)
    {
      LOG.severe("An error has occured while creating the navigation file" + e.getMessage());
    }
    finally
    {
      if (writer != null)
      {
        writer.close();
      }
    }
  }
}
