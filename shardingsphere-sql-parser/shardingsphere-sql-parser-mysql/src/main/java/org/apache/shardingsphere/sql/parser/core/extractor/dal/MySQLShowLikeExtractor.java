/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.sql.parser.core.extractor.dal;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.shardingsphere.sql.parser.core.extractor.api.OptionalSQLSegmentExtractor;
import org.apache.shardingsphere.sql.parser.core.extractor.util.ExtractorUtils;
import org.apache.shardingsphere.sql.parser.core.extractor.util.RuleName;
import org.apache.shardingsphere.sql.parser.sql.segment.dal.ShowLikeSegment;
import org.apache.shardingsphere.sql.parser.sql.value.identifier.IdentifierValue;

import java.util.Map;

/**
 * Show like extractor for MySQL.
 * 
 * @author zhangliang
 */
public final class MySQLShowLikeExtractor implements OptionalSQLSegmentExtractor {
    
    @Override
    public Optional<ShowLikeSegment> extract(final ParserRuleContext ancestorNode, final Map<ParserRuleContext, Integer> parameterMarkerIndexes) {
        Optional<ParserRuleContext> showLikeNode = ExtractorUtils.findFirstChildNode(ancestorNode, RuleName.SHOW_LIKE);
        if (!showLikeNode.isPresent()) {
            return Optional.absent();
        }
        Optional<ParserRuleContext> stringLiteralsNode = ExtractorUtils.findFirstChildNode(showLikeNode.get(), RuleName.STRING_LITERALS);
        Preconditions.checkState(stringLiteralsNode.isPresent());
        String pattern = stringLiteralsNode.get().getText().substring(1, stringLiteralsNode.get().getText().length() - 1);
        return Optional.of(new ShowLikeSegment(stringLiteralsNode.get().getStart().getStartIndex() + 1, stringLiteralsNode.get().getStop().getStopIndex() - 1, new IdentifierValue(pattern)));
    }
}
