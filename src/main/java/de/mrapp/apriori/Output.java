/*
 * Copyright 2017 Michael Rapp
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package de.mrapp.apriori;

import de.mrapp.apriori.Apriori.Configuration;
import de.mrapp.apriori.datastructure.FrequentItemSetTreeSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.SortedSet;

import static de.mrapp.util.Condition.ensureAtLeast;
import static de.mrapp.util.Condition.ensureNotNull;

/**
 * An output of the Apriori algorithm.
 *
 * @param <ItemType> The type of the items, which have been processed by the Apriori algorithm
 * @author Michael Rapp
 * @since 1.0.0
 */
public class Output<ItemType extends Item> implements Serializable, Cloneable {

    /**
     * The constant serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The configuration of the Apriori algorithm.
     */
    private final Configuration configuration;

    /**
     * The time, the Apriori algorithm has been started, in milliseconds.
     */
    private final long startTime;

    /**
     * The time, the Apriori algorithm has been ended, in milliseconds.
     */
    private final long endTime;

    /**
     * The frequent item sets, which have been found by the Apriori algorithm.
     */
    private final FrequentItemSetTreeSet<ItemType> frequentItemSets;

    /**
     * The rule set, which contains the association rules, which have been generated by the Apriori
     * algorithm.
     */
    private final RuleSet<ItemType> ruleSet;

    /**
     * Creates a new output of the Apriori algorithm.
     *
     * @param configuration    The configuration of the Apriori algorithm as an instance of the
     *                         class {@link Configuration}. The configuration may not be null
     * @param startTime        The time, the Apriori algorithm has been started, in milliseconds as
     *                         a {@link Long} value. The time must be at least 0
     * @param endTime          The time, the Apriori algorithm has been ended, in milliseconds as a
     *                         {@link Long} value. The time must be at least the start time
     * @param frequentItemSets The frequent item sets, which have been found by the Apriori
     *                         algorithm as an instance of the type {@link SortedSet} or an empty
     *                         set, if no frequent item sets have been found
     * @param ruleSet          The rule set, which contains the association rules, which have been
     *                         generated by the Apriori algorithm, as an instance of the class
     *                         {@link RuleSet} or null, if the algorithm has not been configured to
     *                         generate any rules
     */
    public Output(@NotNull final Configuration configuration, final long startTime,
                  final long endTime,
                  @NotNull final FrequentItemSetTreeSet<ItemType> frequentItemSets,
                  @Nullable final RuleSet<ItemType> ruleSet) {
        ensureNotNull(configuration, "The configuration may not be null");
        ensureAtLeast(startTime, 0, "The start time must be at least 0");
        ensureAtLeast(endTime, startTime, "The end time must be at least " + startTime);
        ensureNotNull(frequentItemSets, "The frequent item sets may not be null");
        this.configuration = configuration;
        this.startTime = startTime;
        this.endTime = endTime;
        this.frequentItemSets = frequentItemSets;
        this.ruleSet = ruleSet;
    }

    /**
     * Returns the configuration of the Apriori algorithm.
     *
     * @return The configuration of the Apriori algorithm as an instance of the class {@link
     * Configuration}. The configuration may not be null
     */
    @NotNull
    public final Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Returns the time, the Apriori algorithm has been started.
     *
     * @return The time, the Apriori algorithm has been started, in milliseconds as a {@link Long}
     * value
     */
    public final long getStartTime() {
        return startTime;
    }

    /**
     * Returns the time, the Apriori algorithm has been ended.
     *
     * @return The time, the Apriori algorithm has been ended, in milliseconds as a {@link Long}
     * value
     */
    public final long getEndTime() {
        return endTime;
    }

    /**
     * Returns the runtime of the Apriori algorithm.
     *
     * @return The runtime of the Apriori algorithm in milliseconds as a {@link Long} value
     */
    public final long getRuntime() {
        return endTime - startTime;
    }

    /**
     * Returns the frequent item sets, which have been found by the Apriori algorithm.
     *
     * @return The frequent item sets, which have been found by the Apriori algorithm as an instance
     * of the type {@link SortedSet} or an empty set, if no frequent item sets have been found
     */
    @NotNull
    public final SortedSet<ItemSet<ItemType>> getFrequentItemSets() {
        return frequentItemSets;
    }

    /**
     * Returns the association rules, which have been generated by the Apriori algorithm.
     *
     * @return A rule set, which contains the association rules, which have been generated by the
     * Apriori algorithm, as an instance of the class {@link RuleSet} or null, if the algorithm has
     * not been configured to generate any rules
     */
    @Nullable
    public final RuleSet<ItemType> getRuleSet() {
        return ruleSet;
    }

    @Override
    public final Output<ItemType> clone() {
        return new Output<>(configuration.clone(), startTime, endTime, frequentItemSets.clone(),
                ruleSet.clone());
    }

    @Override
    public final String toString() {
        return "configuration=" + configuration.toString() + ",\nstartTime=" + startTime +
                ",\nendTime=" + endTime + ",\nruntime=" + getRuntime() + ",\nfrequentItemSets=" +
                frequentItemSets.toString() + ",\nruleSet=" + ruleSet.toString();
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + configuration.hashCode();
        result = prime * result + (int) (startTime ^ (startTime >>> 32));
        result = prime * result + (int) (endTime ^ (endTime >>> 32));
        result = prime * result + frequentItemSets.hashCode();
        result = prime * result + (ruleSet == null ? 0 : ruleSet.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Output<?> other = (Output<?>) obj;
        if (!configuration.equals(other.configuration))
            return false;
        if (startTime != other.startTime)
            return false;
        if (endTime != other.endTime)
            return false;
        if (!frequentItemSets.equals(other.frequentItemSets))
            return false;
        if (ruleSet == null) {
            if (other.ruleSet != null)
                return false;
        } else if (!ruleSet.equals(other.ruleSet))
            return false;
        return true;
    }

}