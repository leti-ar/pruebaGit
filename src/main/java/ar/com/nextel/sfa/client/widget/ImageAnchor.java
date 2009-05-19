package ar.com.nextel.sfa.client.widget;
import com.google.gwt.dom.client.AnchorElement; 
import com.google.gwt.dom.client.DivElement; 
import com.google.gwt.dom.client.Document; 
import com.google.gwt.user.client.Event; 
import com.google.gwt.user.client.ui.Image; 
import com.google.gwt.user.client.ui.Widget; 

/**
 * Anchor con un Image. Para usar en vez de un Hyperlink
 * 
 * @TODO Debería ir a gwtcommons una vez pulido
 * 
 * @author ramirogm
 * @author http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/4783c9b0cc35e081/a28bac8e0a1457b5
 *
 */
public class ImageAnchor extends Widget { 
    private Image img; 
    private String url; 
    private String target; 
    private DivElement element; 
    private AnchorElement aEl; 
    
    public ImageAnchor(Image img, String url){ 
        initElements(); 
        setImg(img); 
        setUrl(url); 
    } 
    private void initElements() { 
        element = Document.get().createDivElement(); 
        aEl = Document.get().createAnchorElement(); 
        element.appendChild(aEl); 
        setElement(element); 
        sinkEvents(Event.MOUSEEVENTS); 
        setTarget("_blank"); 
    } 
    public void onBrowserEvent(Event event) { 
        if(event.getTypeInt() == Event.ONMOUSEOVER){ 
            aEl.getStyle().setProperty("cursor", "hand"); 
        } 
        super.onBrowserEvent(event); 
    } 
    public ImageAnchor(){ 
        this(null, ""); 
    } 
    /** 
     * @return the img 
     */ 
    public Image getImg() { 
        return img; 
    } 
    /** 
     * @param img the img to set 
     */ 
    public void setImg(Image img) { 
        this.img = img; 
        aEl.appendChild(img.getElement()); 
    } 
    /** 
     * @return the url 
     */ 
    public String getUrl() { 
        return url; 
    } 
    /** 
     * @param url the url to set 
     */ 
    public void setUrl(String url) { 
        this.url = url; 
        aEl.setHref(url); 
    } 
    /** 
     * @return the target 
     */ 
    public String getTarget() { 
        return target; 
    } 
    /** 
     * @param target the target to set 
     */ 
    public void setTarget(String target) { 
        this.target = target; 
        aEl.setTarget(target); 
    } 
} 
