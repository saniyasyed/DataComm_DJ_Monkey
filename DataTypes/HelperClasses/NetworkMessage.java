package DataTypes.HelperClasses;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * This class is used to send objects over the network.
 */
public class NetworkMessage implements Serializable
{
    public Type objType;
    public Object message;
    public int senderId;

    /**
     * Constructor for networkMessage
     * @param objType
     * @param message
     */
    public NetworkMessage(Type objType, Object message)
    {
        this.objType = objType;
        this.message = message;
    }

    public String GetTypeName()
    {
        String typeName = objType.getTypeName();
        String name = typeName.substring(typeName.lastIndexOf(".") + 1);
        return name;
    }
}
