package org.exoplatform.support.cgvaldoise.mop;

import org.apache.commons.lang3.StringUtils;
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
 */
public class PageInjector
{
  private final static Logger LOG = Logger.getLogger(PageInjector.class.getName());
  private final static int TOTAL_PARENT_PAGES = 25;
  private final static int TOTAL_CHILD_PAGES = 25;
  private final static int TOTAL_PAGES = TOTAL_PARENT_PAGES + (TOTAL_PARENT_PAGES * TOTAL_CHILD_PAGES);

  public static void main(String[] args)
  {
    File pages = new File("target/pages.xml");
    if (pages.exists()) {
      pages.delete();
    }
    FileOutputStream fileOutputStream;
    PrintWriter writer = null;
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
        LOG.info("Page " + i + " declaration has been appended to \"" + pages.getAbsolutePath() +"\" file.");
      }
    } catch (FileNotFoundException e)
    {
      LOG.severe("An error has occured while creating the pages file" + e.getMessage());
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
