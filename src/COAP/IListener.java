package COAP;

import Utils.StateData;

/**
 *
 * @author Даниил
 */
public interface IListener {
    public void onCreated(int id);
    public void onUpdated(int id, StateData data);
}
