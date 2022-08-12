package support.handlebars;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Options;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;
import support.version.BlogVersion;

@HandlebarsHelper
public class BlogHandlebarsHelper {
    private static final Logger logger = LoggerFactory.getLogger(BlogHandlebarsHelper.class);

    @Autowired
    private BlogVersion blogVersion;

    public CharSequence staticUrls(String path, Options options) {
        if (Strings.isBlank(path)) {
            return Strings.EMPTY;
        }

        logger.debug("static url : {}", path);
        return new Handlebars.SafeString(String.format("/resources/%s%s", blogVersion.getVersion(), path));
    }
}
