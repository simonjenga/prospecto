package org.soulwing.prospecto.runtime.template;

import java.util.Iterator;
import java.util.Map;

import org.soulwing.prospecto.api.association.ToManyMappedAssociationManager;
import org.soulwing.prospecto.api.template.MapOfValuesNode;
import org.soulwing.prospecto.api.template.ViewNodeVisitor;
import org.soulwing.prospecto.runtime.accessor.Accessor;
import org.soulwing.prospecto.runtime.accessor.ConcreteMapAccessorFactory;
import org.soulwing.prospecto.runtime.accessor.MapAccessor;
import org.soulwing.prospecto.runtime.accessor.MapAccessorFactory;
import org.soulwing.prospecto.runtime.converter.Convertible;

/**
 * A view node that represents a map of values.
 *
 * @author Carl Harris
 */
public class ConcreteMapOfValuesNode extends AbstractViewNode
    implements Convertible, MapOfValuesNode {

  private final MapAccessorFactory mapAccessorFactory;

  private MapAccessor mapAccessor;

  public ConcreteMapOfValuesNode(String name, String namespace) {
    this(name, namespace, Object.class,
        ConcreteMapAccessorFactory.INSTANCE);
  }

  ConcreteMapOfValuesNode(String name, String namespace,
      Class<?> componentType, MapAccessorFactory mapAccessorFactory) {
    super(name, namespace, componentType);
    this.mapAccessorFactory = mapAccessorFactory;
  }

  @Override
  public void setAccessor(Accessor accessor) {
    super.setAccessor(accessor);
    this.mapAccessor = mapAccessorFactory.newAccessor(accessor);
  }

  @Override
  public Class<?> getComponentType() {
    return Object.class;
  }

  @Override
  public Iterator<Map.Entry> iterator(Object model) throws Exception {
    return mapAccessor.iterator(model);
  }

  @Override
  public ToManyMappedAssociationManager<?, ?, ?> getDefaultManager() {
    return mapAccessor;
  }

  @Override
  public Object accept(ViewNodeVisitor visitor, Object state) {
    return visitor.visitMapOfValues(this, state);
  }

}
