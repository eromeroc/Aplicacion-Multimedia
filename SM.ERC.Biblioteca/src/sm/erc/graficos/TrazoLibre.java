/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.erc.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import sm.erc.iu.ModoRelleno;

/**
 * Representa un trazo libre.
 * Proporciona métodos para pintarlo, desplazarlo y comprobar si contiene cierto punto.
 * @version 1.0
 * @author Elena Romero Contreras
 */
public class TrazoLibre extends Figura{
    
    private Path2D libre;
        
    /**
     * Crea un nuevo TrazoLibre.
     * Llama al constructor de Figura para asignar los atributos de dibujo y 
     * crea objeto de la clase Path2D.Double a partir del punto especificado.
     * @param colorTrazo color del trazo
     * @param grosor grosor del trazo
     * @param trazo estilo del trazo
     * @param alisar si se aplica renderización
     * @param transp grado de transparencia
     * @param x coordenada x del punto de inicio del objeto Path2D.Double
     * @param y coordenada y del punto de inicio del objeto Path2D.Double
     */
    public TrazoLibre(Color colorTrazo, int grosor, String trazo, boolean alisar, float transp, double x, double y) {
        super(Color.BLACK, Color.BLACK, colorTrazo, grosor, trazo, ModoRelleno.SIN, alisar, transp);
        this.libre = new Path2D.Double();
        this.libre.moveTo(x,y);
    }
    /**
     * Añade un punto al TrazoLibre dibujando una línea recta desde el punto actual
     * hasta el nuevo punto especificado.
     * @param p nuevo punto del trazo
     */
    public void setLineTo(Point2D p){
        libre.lineTo(p.getX(), p.getY());
    }

    @Override
    public void paint(Graphics2D g2d) {
        super.paintBoundingBox(g2d);
        g2d.setPaint(colorTrazo);
        g2d.setStroke(stroke);
        g2d.setComposite(comp);
        if(alisar)
            g2d.setRenderingHints(render);
          
        g2d.draw(libre);        
    }

    @Override
    public boolean contains(Point2D pos) {
        return libre.contains(pos);
    }

    @Override
    public void setLocation(double difX, double difY) {
        AffineTransform at = new AffineTransform();
        at.translate(difX, difY);
        libre.transform(at);
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return libre.getBounds2D();
    }
    
}
