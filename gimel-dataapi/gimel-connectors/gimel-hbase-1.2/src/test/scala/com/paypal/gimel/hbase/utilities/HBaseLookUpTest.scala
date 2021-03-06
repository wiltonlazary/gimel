/*
 * Copyright 2018 PayPal Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paypal.gimel.hbase.utilities

import org.scalatest.{BeforeAndAfterAll, Matchers}

import com.paypal.gimel.common.catalog.DataSetProperties
import com.paypal.gimel.hbase.conf.HbaseConfigs

class HBaseLookUpTest extends HBaseLocalClient with Matchers with BeforeAndAfterAll {

  ignore ("get") {
    val props : Map[String, String] = Map(HbaseConfigs.hbaseNamespaceKey -> "default",
      HbaseConfigs.hbaseTableKey -> s"""$tableName""",
      HbaseConfigs.hbaseRowKey -> "id",
      HbaseConfigs.hbaseFilter -> "rowKey=10",
      HbaseConfigs.hbaseColumnMappingKey -> "personal:name,personal:address,personal:age,professional:company,professional:designation,professional:salary",
      HbaseConfigs.hbaseOperation -> "get")
    val dataSetName = "HBase.Local.default." + tableName
    val dataSetProperties = DataSetProperties(dataSetName, null, null, props)
    val datasetProps : Map[String, Any] = Map("dataSetProperties" -> dataSetProperties)
    val dataFrame = mockDataInDataFrame(10)
    dataFrame.show(1)
    val df = HBasePut(sparkSession).put(dataSetName, dataFrame, datasetProps)
    val dfLookUp = HBaseLookUp(sparkSession).get(dataSetName, datasetProps)
    dfLookUp.show
  }
}
