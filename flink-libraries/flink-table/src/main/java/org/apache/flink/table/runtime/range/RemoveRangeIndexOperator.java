/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.table.runtime.range;

import org.apache.flink.annotation.Internal;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.operators.OneInputStreamOperator;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.table.dataformat.BaseRow;
import org.apache.flink.table.runtime.AbstractStreamOperatorWithMetrics;
import org.apache.flink.table.runtime.util.StreamRecordCollector;
import org.apache.flink.util.Collector;

/**
 * Remove the range index and return the actual record.
 */
@Internal
public class RemoveRangeIndexOperator extends AbstractStreamOperatorWithMetrics<BaseRow>
		implements OneInputStreamOperator<Tuple2<Integer, BaseRow>, BaseRow> {

	private transient Collector<BaseRow> collector;

	@Override
	public void open() throws Exception {
		super.open();
		this.collector = new StreamRecordCollector<>(output);
	}

	@Override
	public void processElement(
			StreamRecord<Tuple2<Integer, BaseRow>> streamRecord) throws Exception {
		collector.collect(streamRecord.getValue().f1);
	}

	@Override
	public void endInput() throws Exception {

	}
}