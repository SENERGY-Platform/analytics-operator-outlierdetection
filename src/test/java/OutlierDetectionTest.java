/*
 * Copyright 2018 InfAI (CC SES)
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

import org.infai.seits.sepl.operators.Message;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


public class OutlierDetectionTest {

    static OutlierDetection outlierDetection;

    @Test
    public void testRun() throws IOException {
        outlierDetection = new OutlierDetection();
        List<Message> messages = TestMessageProvider.getTestMesssagesSet();
        for (Message m : messages) {
            outlierDetection.config(m);
            outlierDetection.run(m);
            m.addInput("outlier");
            String outlier = m.getInput("outlier").getString();
            if (m.getMessageString().contains("sigma") && outlier.equals("no")) {
                Assert.fail("Detected an outlier that should not be one.");
            }else if(!m.getMessageString().contains("sigma") && outlier.equals("yes")){
                Assert.fail("Did not detected an outlier that should be one.");
            }
        }
    }
}
