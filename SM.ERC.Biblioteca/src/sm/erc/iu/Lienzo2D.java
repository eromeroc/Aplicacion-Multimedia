/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.erc.iu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import sm.erc.graficos.Elipse;
import sm.erc.graficos.Figura;
import sm.erc.graficos.Linea;
import sm.erc.graficos.CoronaCircular;
import sm.erc.graficos.Curva;
import sm.erc.graficos.Rectangulo;
import sm.erc.graficos.RectanguloRedondeado;
import sm.erc.graficos.TrazoLibre;

/**
 * Representa un lienzo de dibujo incluyendo información sobre la forma y atributos activos,
 * proporciona métodos para manejarlos y gestiona eventos de ratón vinculados al proceso de dibujo.
 * 
 * @version 1.0
 * @author Elena Romero Contreras  
 */
public class Lienzo2D extends javax.swing.JPanel {

    //Vector de formas
    private List<Figura> vShape = new ArrayList();
    private Figura selectedShape = null;
    private Point2D pAux;
    private boolean paso;   
    private Rectangle2D areaClip;
    
    private Tool tool;
    private Color color;
    private Color color2;
    private ModoRelleno modoRelleno;
    private Color colorTrazo;
    private int grosor;
    private String estiloTrazo;
    private boolean alisar;  
    private float transp;
    private boolean editar;
    

    /**
     * Crea un Lienzo2D e inicializa los atributos a sus valores por defecto
     */
    public Lienzo2D() {
        initComponents();
        this.color = Color.BLACK;
        this.color2 = Color.BLACK;
        this.modoRelleno = ModoRelleno.SIN;
        this.colorTrazo = Color.BLACK;
        this.estiloTrazo = "Continuo";
        this.grosor = 1;
        this.transp = 1.0f;
        this.editar = false;
        this.alisar = false;
        this.paso = false;
    }
    /**
     * Devuelve el color de relleno activo.
     * @return color de relleno
     */
    public Color getColor(){
        return color;
    }
    /**
     * Modifica color de relleno activo.
     * Además, si está activado el botón de editar y hay una figura no nula seleccionada,
     * llama al método que modifica su color. Redibuja.
     * @param color nuevo color de relleno
     */
    public void setColor(Color color){
        this.color = color;
        if(editar && selectedShape != null){
            selectedShape.setColor(color);
            this.repaint();
        }
    }
    /**
     * Devuelve el color de fondo activo.
     * @return color de fondo
     */
    public Color getColor2(){
        return color2;
    }
    /**
     * Modifica color de fondo activo.
     * Además, si está activado el botón de editar y hay una figura no nula seleccionada,
     * llama al método que modifica su color de fondo. Redibuja.
     * @param color nuevo color de fondo
     */    
    public void setColor2(Color color){
        this.color2 = color;
        //Si está activado el botón de editar y hay una figura seleccionada
        if(editar && selectedShape != null){
            selectedShape.setColor2(color);
            this.repaint();
        }
    }  
    /**
     * Devuelve el grosor de trazo activo.
     * @return grosor de trazo
     */
    public int getGrosor(){
        return grosor;
    }
    /**
     * Modifica grosor de trazo activo.
     * Además, si está activado el botón de editar y hay una figura no nula seleccionada,
     * llama al método que modifica su grosor de trazo. Redibuja.
     * @param grosor nuevo grosor de trazo
     */
    public void setGrosor(int grosor){
        this.grosor = grosor;
        if(editar && selectedShape != null){
            selectedShape.setGrosor(grosor);
            this.repaint();
        }
    }
    /**
     * Devuelve el color de trazo activo.
     * @return color de trazo
     */
    public Color getColorTrazo(){
        return colorTrazo;
    }
    /**
     * Modifica color de trazo activo.
     * Además, si está activado el botón de editar y hay una figura no nula seleccionada,
     * llama al método que modifica su color de trazo. Redibuja.
     * @param colorTrazo nuevo color de trazo
     */
    public void setColorTrazo(Color colorTrazo){
        this.colorTrazo = colorTrazo;
        if(editar && selectedShape != null){
            selectedShape.setColorTrazo(colorTrazo);
            this.repaint();
        } 
    }
    /**
     * Devuelve estilo de trazo activo.
     * @return estilo de trazo
     */
    public String getEstiloTrazo(){
        return estiloTrazo;
    }
    /**
     * Modifica estilo de trazo activo.
     * Además, si está activado el botón de editar y hay una figura no nula seleccionada,
     * llama al método que modifica su estilo de trazo. Redibuja.
     * @param trazo nuevo estilo de trazo
     */
    public void setEstiloTrazo(String trazo){
        this.estiloTrazo = trazo;
        if(editar && selectedShape != null){
            selectedShape.setEstiloTrazo(trazo);
            this.repaint();
        }        
    }    
    /**
     * Devuelve herramienta de dibujo activa.
     * @return herramienta de dibujo
     */
    public Tool getTool(){
        return tool;
    }  
    /**
     * Modifica herramienta de dibujo activa.
     * Cambia a cursor de dibujo y desactiva opción de editar si está activada
     * @param tool nueva herramienta de dibujo
     */    
    public void setTool(Tool tool){
        this.tool = tool;
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        if(editar) //Si está activada la opción de editar, la desactivamos
            this.setEditar(false);
    }  
 
    /**
     * Devuelve modo de relleno activo.
     * @return modo de relleno
     */
    public ModoRelleno getModoRelleno(){
        return modoRelleno;
    }
    /**
     * Modifica modo de relleno activo.
     * Además, si está activado el botón de editar y hay una figura no nula seleccionada,
     * llama al método que modifica su modo de relleno. Redibuja.
     * @param modoRelleno nuevo modo de relleno
     */
    public void setModoRelleno(ModoRelleno modoRelleno){
        this.modoRelleno = modoRelleno;
        if(editar && selectedShape != null){
            selectedShape.setModoRelleno(modoRelleno);
            this.repaint();
        }
    }
    /**
     * Devuelve si el alisado está activado/desactivado.
     * @return alisado activado/desactivado
     */
    public boolean getAlisar(){
        return alisar;
    }
    /**
     * Modifca si el alisado está activado/desactivado.
     * @param alisar si activa o desactiva el alisado
     */
    public void setAlisar(boolean alisar){
        this.alisar = alisar;
        if(editar && selectedShape != null){
            selectedShape.setAlisar(alisar);
            this.repaint();
        }
    }
    /**
     * Devuelve el grado de transparencia.
     * @return grado de transparencia
     */
    public float getTransp(){
        return transp;
    }
    /**
     * Modifica grado de transparencia activo.
     * Además, si está activado el botón de editar y hay una figura no nula seleccionada,
     * llama al método que modifica su grado de transparencia. Redibuja.
     * @param transp nuevo grado de transparencia
     */
    public void setTransp(float transp){
        this.transp = transp;
        if(editar && selectedShape != null){
            selectedShape.setTransp(transp);
            this.repaint();
        }
    }
    /**
     * Devuelve si se puede editar
     * @return editar activado/desactivado
     */
    public boolean getEditar(){
        return editar;
    }
    /**
     * Modifica si la edición está activada/desactivada.
     * Si desactivamoss edición, desactivamos selección de la última figura seleccionada.
     * Redibuja.
     * @param editar activa/desactiva la edición
     */
    public void setEditar(boolean editar){
        if(editar){
            this.editar = true;
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        else{
            this.editar = false;
            if(selectedShape != null)
                selectedShape.setSeleccionada(false);
        }
        this.repaint();
    }
    
    /**
     * Modifica área clip. 
     * @param area área clip
     */
    public void setAreaClip(Rectangle2D area){
        this.areaClip = area;
    }
    /**
     * Cambia posición en el vector de figuras de la figura seleccionada.
     * Si la edición está activada y hay alguna figura seleccionada, obtenemos indice y la quitamos del vector.
     * Según el orden indicado la añadimos al vector en la nueva posición.
     * @param orden cadena de texto que contiene el nuevo orden
     */
    public void setOrden(String orden){
        
        if(editar && selectedShape !=null){
            int index = vShape.indexOf(selectedShape);
            
            switch (orden) {
                case "Enviar al fondo":
                    vShape.remove(index);
                    vShape.add(0,selectedShape);
                break;
                case "Traer al frente":
                    vShape.remove(index);
                    int lastIndex = vShape.size();
                    vShape.add(lastIndex,selectedShape);
                break;
                case "Enviar atrás":
                    if(index != 0){
                        vShape.remove(index);
                        vShape.add(index-1,selectedShape);
                    }
                break;
                case "Traer adelante":
                    if(index != vShape.size()-1){
                        vShape.remove(index);
                        vShape.add(index+1,selectedShape);
                    }                    
                break;
                default:
            }
            this.repaint();
        }
    }   
    /**
     * Crea figura según herramienta activa.
     * Distingue casos según la herramienta y crea figura del mismo tipo que la herramienta activa,
     * tomando como punto inicial el punto pasado.
     * Añade figura al vector de figuras del lienzo.
     * @param p punto inicial de la figura
     */
    public void createShape(Point2D p){       
        switch(tool){
            case PUNTO:
                Linea punto = new Linea(colorTrazo, grosor, estiloTrazo, alisar, transp,
                        p.getX(),p.getY(),p.getX(),p.getY());
                vShape.add(punto);
            break;
            case LINEA:
                Linea line = new Linea(colorTrazo, grosor, estiloTrazo, alisar, transp,
                        p.getX(),p.getY(),p.getX(),p.getY());
                vShape.add(line);
            break;
            case RECTANGULO:
                Rectangulo rect = new Rectangulo(color, color2, colorTrazo, grosor,estiloTrazo, modoRelleno,
                        alisar, transp, p.getX(),p.getY(),0,0); //x,y,width,height 
                vShape.add(rect);                
            break;
            case ROUND_RECT:
                RectanguloRedondeado round_rect = new RectanguloRedondeado(color, color2, colorTrazo, grosor,estiloTrazo, modoRelleno,
                        alisar, transp, p.getX(),p.getY(),0,0,20,20); //x,y,width,height 
                vShape.add(round_rect);                
            break;
            
            case ELIPSE:
                Elipse elip = new Elipse(color, color2, colorTrazo, grosor, estiloTrazo, modoRelleno, alisar, transp,
                        p.getX(),p.getY(),0,0); //x,y,width,height
                vShape.add(elip);
            break;
            case LIBRE:
                TrazoLibre libre = new TrazoLibre(colorTrazo, grosor, estiloTrazo, alisar, transp,
                        p.getX(),p.getY());
                vShape.add(libre);
            break;
            case CURVA:
                Curva curva = new Curva(colorTrazo, grosor, estiloTrazo, alisar, transp,p.getX(),p.getY(),p.getX(),p.getY(),p.getX(),p.getY());
                vShape.add(curva);
            break;
            case CORONA:
                CoronaCircular area = new CoronaCircular(color, color2, colorTrazo, grosor, estiloTrazo, modoRelleno, alisar, transp,
                        p.getX(),p.getY(),0,0); 
                vShape.add(area);
            break;
            default:
            break;            
        }        
    }
    
    
    /**
     * Devuelve la última figura del vector de figuras del lienzo.
     * @return ultima figura del vector de figuras
     */
    public Figura getLastShape(){
        Figura lastShape;
        if(vShape.isEmpty())
            lastShape = null;
        else
            lastShape = vShape.get(vShape.size()-1);
        
        return lastShape;
    }
    
    /**
     * Actualiza última figura.
     * Obtiene última figura del vector de figuras.
     * Distingue casos según herramienta activa y llama al método que 
     * modifica el tamaño de la figura extendiéndola hasta el punto p2.
     * Redibuja.
     * @param p1 punto inicial
     * @param p2 punto final
     */
    public void updateShape(Point2D p1, Point2D p2){
        
        Figura lastShape = this.getLastShape();  
        switch(tool){
            case PUNTO:            
            break;
            case LINEA:
                Linea linea = (Linea)lastShape;
                linea.setLine(p1, p2);
            break;               
            case RECTANGULO:
                Rectangulo rect =(Rectangulo)lastShape;
                rect.setFrameFromDiagonal(p1,p2);
            break;
            case ROUND_RECT:
                RectanguloRedondeado roundRect = (RectanguloRedondeado)lastShape;
                roundRect.setFrameFromDiagonal(p1, p2);
            break;
            case ELIPSE:
                Elipse elip =(Elipse)lastShape;
                elip.setFrameFromDiagonal(p1,p2);
            break;
            case LIBRE:
                TrazoLibre libre= (TrazoLibre)lastShape;
                libre.setLineTo(p2);
            break;
            case CURVA:
                Curva curva = (Curva)lastShape;
                curva.setCurve(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p2.getX(), p2.getY());
            break;
            case CORONA:
                CoronaCircular area = (CoronaCircular) lastShape;
                area.setFrameFromDiagonal(p1,p2);
            break;
            default:
            break;
        }            
        this.repaint();  
    }
    
    /**
     * Devuelve figura seleccionada.
     * Desactivamos selección de figura anterior.
     * Recorremos vector de figuras. Si alguna contiene al punto, se activa 
     * la selección de la figura y se devuelve esa figura.
     * @param pos punto a comprobar si está contenido en alguna figura
     * @return figura que contiene al punto pasado
     */
    public Figura getSelectedShape(Point2D pos){  
        if(selectedShape != null)   
            selectedShape.setSeleccionada(false);
            
        for(Figura s:vShape)
            if(s.contains(pos)){
                s.setSeleccionada(true);
                return s;    
            }    
        return null;        
    }
    
    /**
     * Pinta vector de figuras del lienzo.
     * Selecciona área clip.
     * Para cada figura del vector de figuras,
     * llama al método que la pinta.
     * @param g 
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.clip(areaClip);       
        for(Figura s: vShape){
            s.paint(g2d);   //Método que pinta la forma s
        }
        
    }
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Gestiona el evento que se produce al pulsar el ratón.
     * Obtiene punto sobre el que se pulsa.
     * Si podemos editar, comprobamos si alguna figura contiene al punto.
     * Si no, si la herramienta no es una  curva llamamos al método que crea la figura.
     * Si es una curva, si paso está desactivado, llamamos al método que crea la figura.
     * Si paso está activado, modificamos la curva añadiendo el punto pulsado como punto de control
     * @param evt evento
     */
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        pAux = evt.getPoint();
        
        if(editar){
            selectedShape = getSelectedShape(pAux);
            this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        }
        else if(tool!=null){
            
            if(tool != Tool.CURVA){ 
                createShape(pAux);
            }
            else{
                if(!paso){ //Si no he dado el primer paso, la creo
                    createShape(pAux);
                }
                else{   //Si he dado el primer paso, pongo punto de control
                    Figura lastShape = this.getLastShape();  
                    Curva curva = (Curva)lastShape;
                    curva.setCurve(curva.getX1(), curva.getY1(), evt.getPoint().getX(), evt.getPoint().getY(),
                        curva.getX2(), curva.getY2()); 
                    this.repaint();
                }                
            }                
        }  
    }//GEN-LAST:event_formMousePressed

    /**
     * Gestiona el evento que se produce al arrastrar el ratón.
     * Si la edición está activada y la figura seleccionada no es nula, calculamos la distancia a desplazar
     * y llamamos al método que mueve la figura seleccionada. Redibujamos
     * Si no, si la herramienta no es nula y no es una curva, o es una curva en la que no se ha terminado el primer paso
     * actualizamos la figura.
     * @param evt evento
     */
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if(editar){
            if(selectedShape != null){
                double difX = evt.getPoint().getX() - pAux.getX();
                double difY = evt.getPoint().getY() - pAux.getY();
                pAux = evt.getPoint();
                selectedShape.setLocation(difX, difY);      
            }
            this.repaint();
        }                    
        else if(tool!=null){
            if(tool!= Tool.CURVA)
                updateShape(pAux, evt.getPoint());
            else{
                if(!paso) //Si no he terminado el primer paso, la actualizo
                    updateShape(pAux, evt.getPoint());
            }
        }
        
    }//GEN-LAST:event_formMouseDragged

    /**
     * Gestiona el evento que se produce al soltar el ratón.
     * Si la herramienta no es una curva llamamos al método que gestiona el evento de arrastre de ratón
     * Si es una curva y no hemos terminado el paso, lo activmos. En caso contrario, lo desactivamos.
     * @param evt evento
     */
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        
        if(tool != Tool.CURVA)
            this.formMouseDragged(evt);
        else{
            if(!paso){
                this.formMouseDragged(evt);
                paso =true;
            }else
                paso=false;
        }
            
        if(editar)
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_formMouseReleased
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
