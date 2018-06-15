/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.erc.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import sm.erc.iu.ModoRelleno;

/**
 * Clase abstracta que representa figuras geométricas.
 * Proporciona métodos que permiten acceder y actualizar sus atributos.
 * @version 1.0
 * @author Elena Romero Contreras
 */
public abstract class Figura {
    
    /**
     * Color de relleno (frente)
     */
    protected Color color;
    /**
     * Color de relleno (fondo)
     */
    protected Color color2;
    /**
     * Color del trazo
     */
    protected Color colorTrazo;
    /**
     * Grosor del trazo
     */
    protected int grosor;
    /**
     * Modo de relleno
     */
    protected ModoRelleno modoRelleno;
    /**
     * Si se aplica renderizado
     */
    protected boolean alisar;
    /**
     * Grado de transparencia
     */
    protected float transp;
    /**
     * Estilo del trazo
     */
    protected String estiloTrazo;
    /**
     * Si está seleccionada
     */
    protected boolean seleccionada;

    /**
     * Trazo
     */
    protected BasicStroke stroke;
    /**
     * Renderización
     */
    protected RenderingHints render;
    /**
     * Composición alpha
     */
    protected Composite comp;
    
    /**
     * Constructor Figura, que no puede ser instanciada directamente por ser clase abstracta.
     * Se da valor al atributo stroke a partir de los valores de grosor, estilo trazo y color trazo introducidos
     * El atributo seleccionada será false por defecto.
     * @param color color de relleno (frente)
     * @param color2 color de relleno (fondo)
     * @param colorTrazo color de trazo
     * @param grosor grosor de trazo
     * @param trazo estilo de trazo
     * @param modoRelleno modo de relleno
     * @param alisar si se aplica renderización
     * @param transp grado de transparencia
     */
    protected Figura(Color color, Color color2, Color colorTrazo, int grosor, String trazo, ModoRelleno modoRelleno,
            boolean alisar, float transp){
        this.color = color;
        this.color2 = color2;
        this.colorTrazo = colorTrazo;
        this.estiloTrazo = trazo;
        this.grosor = grosor;
        this.modoRelleno = modoRelleno;
        this.alisar = alisar;
        this.transp = transp;
        this.seleccionada = false;
        
        this.setStroke();
        this.render = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        this.comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,transp);
    }
    /**
     * Modifica color de relleno de esta Figura.
     * @param color nuevo color de relleno (frente)
     */
    public void setColor(Color color){
        this.color = color;
    }
    /**
     * Devuelve color de relleno de esta Figura.
     * @return color de relleno (frente)
     */
    public Color getColor(){
        return color;
    } 
    /**
     * Modifica color de relleno (fondo) de esta Figura.
     * @param color nuevo color de relleno (fondo)
     */
    public void setColor2(Color color){
        this.color2 = color;
    } 
    /**
     * Devuelve color de relleno (fondo) de esta Figura.
     * @return color de relleno (fondo)
     */
    public Color getColor2(){
        return color2;
    } 
    /**
     * Modifica color de trazo de esta Figura.
     * @param colorTrazo neuvo color de trazo
     */
    public void setColorTrazo(Color colorTrazo){
        this.colorTrazo = colorTrazo;
    }
    /**
     * Devuelve color de trazo de esta Figura
     * @return color de  trazo
     */
    public Color getColorTrazo(){
        return colorTrazo;
    }
    /**
     * Modifica trazo de esta Figura.
     * Distinguimos casos según estilo de trazo y se asigna nuevo BasicStroke con los
     * valores actuales de grosor y estilo de trazo
     */
    public void setStroke(){        
        if(this.estiloTrazo.equals("Continuo")){
            this.stroke = new BasicStroke(this.grosor);
        }
        else if(this.estiloTrazo.equals("Discontinuo")){  //Discontinuo rayas
            float[] dash = {5f,5f};
            this.stroke = new BasicStroke(this.grosor, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 1.0f, dash, 2.0f);
        }else{                                  //Discontinuo punto
            float[] dash = {1.5f,1.5f};
            this.stroke = new BasicStroke(this.grosor, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 1.0f, dash, 2.0f);
        }        
    }
    /**
     * Devuelve grosor de esta Figura
     * @return grosor del trazo
     */
    public int getGrosor(){
        return grosor;
    }
    /**
     * Modifica grosor de trazo de esta Figura.
     * Llama al método que modifica el trazo de esta Figura.
     * @param grosor nuevo grosor del trazo
     */
    public void setGrosor(int grosor){
        this.grosor = grosor;
        setStroke();
    }
    /**
     * Devuelve estilo de trazo de esta Figura
     * @return estilo de trazo
     */
    public String getEstiloTrazo(){
        return estiloTrazo;
    }
    /**
     * Modifica estilo de trazo de esta Figura.
     * Llama al método que modifica el trazo de esta Figura.
     * @param estiloTrazo nuevo estilo de trazo
     */
    public void setEstiloTrazo(String estiloTrazo){
        this.estiloTrazo = estiloTrazo;
        setStroke();
    }
    /**
     * Activa/desactiva alisado.
     * @param alisar true si se aplica alisado
     */
    public void setAlisar(boolean alisar){
        this.alisar = alisar;       
    }
    /**
     * Devuelve si está activado/desactivado el alisado
     * @return true si se aplica alisado
     */
    public boolean getAlisar(){
        return alisar;
    }
    /**
     * Modifica el grado de transparencia de esta Figura.
     * @param transp nuevo grado de transparencia
     */
    public void setTransp(float transp){
        this.transp = transp;
        this.comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,transp);
    }
    /**
     * Devuelve grado de transparencia de esta Figura.
     * @return grado de transparencia
     */
    public float getTransp(){
        return transp;
    }
    /**
     * Modifica modo de relleno de esta Figura.
     * @param modo modo de relleno
     */
    public void setModoRelleno(ModoRelleno modo){
        this.modoRelleno = modo;
    }
    /**
     * Devuelve modo de relleno de esta Figura.
     * @return 
     */
    public ModoRelleno getModoRelleno(){
        return modoRelleno;
    }
    /**
     * Activa/desactiva selección de esta Figura
     * @param selec true si está seleccionada
     */
    public void setSeleccionada(boolean selec){
        this.seleccionada = selec;
    }
    /**
     * Devuelve si esta Figura está seleccionada.
     * @return true si esta Figura está seleccionada
     */
    public boolean getSeleccionada(){
        return seleccionada;
    }
   
    /**
     * Si la figura está seleccionada, dibuja su bounding box en el 
     * contexto gráfico indicado.
     * @param g2d gráfico especificado
     */
    public void paintBoundingBox(Graphics2D g2d){
        if(seleccionada){
            g2d.setPaint(Color.GRAY);
            float[] dash = {2f,2f};
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 1.0f, dash, 2.0f));
            g2d.draw(this.getBoundingBox());
        }
    }
    
    /**
     * Pinta la figura con sus atributos
     * en el gráfico especificado.
     * Llama al método que dibuja el rectángulo delimitador si la figura está seleccionada.
     * Se activan los atributos de la Figura (colores, trazo, grado de transparencia y alisado)
     * y se rellena o dibuja la forma según modo de relleno
     * @param g2d gráfico especificado
     */
    abstract public void paint(Graphics2D g2d); 
    /**
     * Comprueba si la figura contiene al punto especificado.
     * @param pos punto a comprobar si está contenido en la figura
     * @return true si contiene al punto, false si no
     */
    abstract public boolean contains(Point2D pos);
    /**
     * Desplaza la figura las distancias especificadas en el eje horizontal y vertical
     * @param dx distancia a desplazar en horizontal
     * @param dy distancia a desplazar en vertical
     */
    abstract public void setLocation(double dx, double dy);
    /**
     * Devuelve el rectángulo que delimita la figura
     * @return rectángulo delimitador
     */
    abstract public Rectangle2D getBoundingBox();
}
