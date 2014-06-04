/* with
 DHash|QHash|LHash hash
 char|byte|short|int|long|float|double key
 short|byte|char|int|long|float|double|obj value
*/
/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.collect.impl.hash;

import net.openhft.collect.*;
import net.openhft.collect.map.CharShortMap;
import net.openhft.collect.map.hash.HashCharShortMapFactory;

import java.util.Map;


public abstract class DHashCharShortMapFactorySO/*<>*/
        /* if !(float|double key) */
        extends CharDHashFactory/* if !(LHash hash) */<MutableDHashCharShortMapGO/*<>*/>/* endif */
        /* endif */
        implements HashCharShortMapFactory/*<>*/ {

    /* if float|double key */
    final HashConfig hashConf;
    final HashConfigWrapper configWrapper;
    /* endif */

    DHashCharShortMapFactorySO(/* if !(float|double key) */CharHashConfig
            /* elif float|double key //HashConfig// endif */ conf) {
        /* if !(float|double key) */
        super(conf);
        /* elif float|double key */
        hashConf = conf;
        configWrapper = new HashConfigWrapper(conf);
        /* endif */
    }

    /* define p1 *//* if obj value //<V2 extends V>// endif *//* enddefine */

    /* define p2 *//* if obj value //<V2>// endif *//* enddefine */

    /* define pv *//* if !(obj value) //short// elif obj value //V2// endif *//* enddefine */

    /* if !(float|double key) && !(LHash hash) */
    @Override
    MutableDHashCharShortMapGO/*<>*/ createNewMutable(int expectedSize, char free, char removed) {
        MutableDHashCharShortMapGO/*<>*/ map = uninitializedMutableMap();
        map.init(configWrapper, expectedSize, free, removed);
        return map;
    }
    /* elif float|double key */
    @Override
    public HashConfig getConfig() {
        return hashConf;
    }
    /* endif */

    /* with Mutable|Updatable|Immutable mutability */
    /*p1*/ MutableDHashCharShortMapGO/*p2*/ uninitializedMutableMap() {
        return new MutableDHashCharShortMap/*p2*/();
    }
    /* endwith */

    /* with Mutable|Updatable mutability */
    @Override
    public /*p1*/ MutableDHashCharShortMapGO/*p2*/ newMutableMap(int expectedSize) {
        /* if float|double key */
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.init(configWrapper, expectedSize);
        return map;
        /* elif !(float|double key) && !(LHash hash) && Mutable mutability */
        // noinspection unchecked
        return (MutableDHashCharShortMapGO/*p2*/) newMutableHash(expectedSize);
        /* elif LHash hash || Updatable mutability */
        MutableDHashCharShortMapGO/*p2*/ map = uninitializedMutableMap();
        map.init(configWrapper, expectedSize, getFree());
        return map;
        /* endif */
    }

    /* define ev */
    /* if !(obj value) //Short// elif obj value //? extends V2// endif */
    /* enddefine */

    /* if Updatable mutability */
    @Override
    public /*p1*/ MutableDHashCharShortMapGO/*p2*/ newMutableMap(
            Map<Character, /*ev*/Short/**/> map) {
        if (map instanceof CharShortMap) {
            if (map instanceof CharShortDHash) {
                CharShortDHash hash = (CharShortDHash) map;
                if (hash.hashConfig().equals(hashConf)) {
                    MutableDHashCharShortMapGO/*p2*/ res = uninitializedMutableMap();
                    res.copy(hash);
                    return res;
                }
            }
            MutableDHashCharShortMapGO/*p2*/ res = newMutableMap(map.size());
            res.putAll(map);
            return res;
        }
        MutableDHashCharShortMapGO/*p2*/ res = newMutableMap(map.size());
        for (Map.Entry<Character, /*ev*/Short/**/> entry : map.entrySet()) {
            res.put(entry.getKey(), entry.getValue());
        }
        return res;
    }
    /* endif */
    /* endwith */
}