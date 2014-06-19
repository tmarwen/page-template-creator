package org.exoplatform.support.cgvaldoise.mop;

import org.exoplatform.support.cgvaldoise.page.PageBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * Created by eXo Platform MEA on 12/06/14.
 *
 * @author <a href="mailto:mtrabelsi@exoplatform.com">Marwen Trabelsi</a>
 *
 * After building the maven project, execute blow command to generate both pages and
 * navigation xml declaration:
 * java -jar cgvaldoise-143-page-template-creator-x.y.jar PAGE_TYPE PARENT_PAGES_NUMBER CHILD_PAGES_NUMBER SITE_NAME
 *
 * e.g. java -jar cgvaldoise-143-page-template-creator-1.0.jar simple 15 15 my-site-1
 *
 */
public class PageInjector
{
  private final static Logger LOG = Logger.getLogger(PageInjector.class.getName());
  private final static int TOTAL_PARENT_PAGES = 10;
  private final static int TOTAL_CHILD_PAGES = 10;
  private final static int TOTAL_PAGES = TOTAL_PARENT_PAGES + (TOTAL_PARENT_PAGES * TOTAL_CHILD_PAGES);

  public static void main(String[] args)
  {
    File templatesDirectory = new File("Templates");
    if (!templatesDirectory.exists())
    {
      templatesDirectory.mkdir();
    }

    File pages = new File(templatesDirectory, "pages.xml");
    if (pages.exists()) {
      pages.delete();
    }
    FileOutputStream fileOutputStream;
    PrintWriter writer = null;

    if (args.length >= 4)
    {
      boolean isSimple = args[0].equals("simple") ? true : false;
      int totalParentPages = Integer.parseInt(args[1]);
      int totalChildPages = Integer.parseInt(args[2]);
      String siteName = args[3];
      int totalPages = totalParentPages + (totalParentPages * totalChildPages);

      try
      {
        fileOutputStream = new FileOutputStream(pages);
        writer = new PrintWriter(fileOutputStream);
        for (int i = 1; i <= totalPages; i++)
        {
          StringBuilder builder = new StringBuilder();
          if (isSimple)
          {
            PageBuilder.buildSimplePage(builder, String.valueOf(i));  //Choose one of PageBuilder templates
          }
          else
          {
            PageBuilder.buildCatalogPage(builder, String.valueOf(i));  //Choose one of PageBuilder templates
          }
          writer.println(builder.toString());
          writer.flush();
          LOG.info("Page " + i + " declaration has been appended to \"" + pages.getAbsolutePath() + "\" file.");
        }
      } catch (FileNotFoundException e)
      {
        LOG.severe("An error has occured while creating the pages file: " + e.getMessage());
      } finally
      {
        if (writer != null)
        {
          writer.close();
        }
      }
      NavigationCloner.createPagesNavigation(templatesDirectory, isSimple, totalParentPages, totalChildPages, siteName);
    }
    else
    {
      try
      {
        fileOutputStream = new FileOutputStream(pages);
        writer = new PrintWriter(fileOutputStream);
        for (int i = 1; i <= PageInjector.TOTAL_PAGES; i++)
        {
          StringBuilder builder = new StringBuilder();
          PageBuilder.buildSimplePage(builder, String.valueOf(i));  //Choose one of PageBuilder templates
          writer.println(builder.toString());
          writer.flush();
          LOG.info("Page " + i + " declaration has been appended to \"" + pages.getAbsolutePath() + "\" file.");
        }
      } catch (FileNotFoundException e)
      {
        LOG.severe("An error has occured while creating the pages file: " + e.getMessage());
      } finally
      {
        if (writer != null)
        {
          writer.close();
        }
      }
      NavigationCloner.createPagesNavigation(templatesDirectory, true, PageInjector.TOTAL_PARENT_PAGES, PageInjector.TOTAL_CHILD_PAGES, "my-site");
    }
  }
}
