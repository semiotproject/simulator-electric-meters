package ru.semiot.simulator.electricmeter.coap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.semiot.simulator.electricmeter.utils.StateData;

/**
 *
 * @author Daniil Garayzuev <garayzuev@gmail.com>
 */
public class TestimonialStore {

    private final List<IListener> listeners = new ArrayList<>();
    private static final Map<Integer, StateData> data = new HashMap<>();
    private static final TestimonialStore instance = new TestimonialStore();
    private static int externalID;

    public static int getID() {
        return externalID;
    }

    public TestimonialStore() {
    }

    public static TestimonialStore getInstance() {
        return instance;
    }

    public void addListener(IListener listener) {
        listeners.add(listener);
    }

    public StateData getData(int id) {
        if (data.containsKey(id)) {
            return data.get(id);
        } else {
            return null;
        }
    }

    public void setData(int _id, StateData _data) {
        if (!data.containsKey(_id)) {
            for (IListener l : listeners) {
                externalID = _id;
                l.onCreated(_id);
            }
            data.put(_id, _data.clone());
        } else {
            data.put(_id, _data.clone());
            for (IListener l : listeners) {
                l.onUpdated(_id, _data);
            }
        }

    }
}
