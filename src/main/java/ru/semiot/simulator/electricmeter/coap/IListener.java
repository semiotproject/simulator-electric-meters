package ru.semiot.simulator.electricmeter.coap;

import ru.semiot.simulator.electricmeter.utils.StateData;

/**
 *
 * @author Даниил
 */
public interface IListener {
    public void onCreated(int id);
    public void onUpdated(int id, StateData data);
}
